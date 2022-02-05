package br.com.boasaude.gisa.user.service;

import br.com.boasaude.gisa.user.domain.GisaUser;
import br.com.boasaude.gisa.user.dto.UserDto;
import br.com.boasaude.gisa.user.repository.UserRepository;
import com.auth0.exception.Auth0Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ManagementAPIService managementAPIService;

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
        return getUserDto(gisaUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
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
            return getUserDto(userRepository.save(gisaUser));
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
                .cpf(gisaUser.getCpf())
                .email(gisaUser.getEmail())
                .nome(gisaUser.getNome())
                .id(gisaUser.getUserId())
                .areaAtuacao(gisaUser.getAreaAtuacao())
                .telefone(gisaUser.getTelefone())
                .build();
    }


}
