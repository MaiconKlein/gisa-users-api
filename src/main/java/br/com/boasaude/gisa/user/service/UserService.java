package br.com.boasaude.gisa.user.service;

import br.com.boasaude.gisa.user.domain.GisaUser;
import br.com.boasaude.gisa.user.dto.UserDto;
import br.com.boasaude.gisa.user.kafka.UserProducer;
import br.com.boasaude.gisa.user.repository.UserRepository;
import com.auth0.exception.Auth0Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ManagementAPIService managementAPIService;
    private final UserProducer userProducer;

    @Transactional(readOnly = true)
    public List<UserDto> listar() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(gisaUser -> {
            UserDto userDto = getUserDto(gisaUser);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto criar(UserDto userDto, String role) throws Auth0Exception {

        GisaUser gisaUser = getUser(userDto);
        managementAPIService.atualizarUserRole(role, gisaUser.getEmail());
        userRepository.save(gisaUser);
        UserDto userDtoRetorno = getUserDto(gisaUser);
        userProducer.send(userDto, "CRIACAO");
        return userDtoRetorno;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        userProducer.send(UserDto.builder().id(id).build(), "DELECAO");
    }

    @Transactional
    public UserDto atualizar(UserDto userDto, String role) throws Auth0Exception {
        Optional<GisaUser> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isPresent()) {
            GisaUser gisaUser = userOptional.get();
            gisaUser.setCpf(userDto.getCpf());
            gisaUser.setEmail(userDto.getEmail());
            gisaUser.setNome(userDto.getNome());
            gisaUser.setAreaAtuacao(userDto.getAreaAtuacao());
            gisaUser.setTelefone(userDto.getTelefone());

            managementAPIService.atualizarMetadata(userDto, role);
            UserDto userDtoRetorno = getUserDto(userRepository.save(gisaUser));
            userProducer.send(userDto, "ATUALIZACAO");
            return userDtoRetorno;
        }

        throw new EntityNotFoundException("Usuário não cadastrado");
    }

    private GisaUser getUser(UserDto userDto) {
        return GisaUser.builder()
                .cpf(userDto.getCpf())
                .nome(userDto.getNome())
                .email(userDto.getEmail())
                .userId(userDto.getId())
                .telefone(userDto.getTelefone())
                .areaAtuacao(userDto.getAreaAtuacao())
                .build();
    }

    private UserDto getUserDto(GisaUser gisaUser) {
        return UserDto.builder()
                .id(gisaUser.getUserId())
                .cpf(gisaUser.getCpf())
                .email(gisaUser.getEmail())
                .nome(gisaUser.getNome())
                .id(gisaUser.getUserId())
                .areaAtuacao(gisaUser.getAreaAtuacao())
                .telefone(gisaUser.getTelefone())
                .build();
    }

}
