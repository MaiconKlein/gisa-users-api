package br.com.boasaude.gisa.conveniado.service;

import br.com.boasaude.gisa.conveniado.domain.User;
import br.com.boasaude.gisa.conveniado.dto.UserDto;
import br.com.boasaude.gisa.conveniado.repository.UserRepository;
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

    @Transactional(readOnly = true)
    public List<UserDto> listar() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(user ->
        {
            UserDto userDto = getUserDto(user);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDto criar(UserDto userDto, String token) throws Auth0Exception {

        User user = getUser(userDto);
        userRepository.save(user);

        managementAPIService.atualizarUserRole(token, user.getEmail());
        return getUserDto(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto atualizar(UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(user.getRole());
            user.setCpf(userDto.getCpf());
            user.setEmail(userDto.getEmail());
            user.setNome(userDto.getNome());

            return getUserDto(userRepository.save(user));
        }
        throw new EntityNotFoundException("Usuário não cadastrado");
    }

    private User getUser(UserDto userDto) {
        return User.builder()
                .cpf(userDto.getCpf())
                .nome(userDto.getNome())
                .email(userDto.getEmail())
                .userId(userDto.getId())
                .build();
    }

    private UserDto getUserDto(User user) {
        return UserDto.builder()
                .cpf(user.getCpf())
                .email(user.getEmail())
                .nome(user.getNome())
                .id(user.getUserId())
                .role(user.getRole())
                .build();
    }


}
