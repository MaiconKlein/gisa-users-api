package br.com.boasaude.gisa.user.controller;

import br.com.boasaude.gisa.user.dto.UserDto;
import br.com.boasaude.gisa.user.service.UserService;
import com.auth0.exception.Auth0Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/private/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> listar() {
        return new ResponseEntity(userService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> criar(@Valid @RequestBody UserDto userDto,
                                         @NotEmpty @RequestHeader String role) throws Auth0Exception {
        UserDto dto = userService.criar(userDto, role);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> atualizar(@Valid @RequestBody UserDto userDto,
                                             @NotEmpty @RequestHeader String role) throws Auth0Exception {
        return new ResponseEntity(userService.atualizar(userDto, role), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@Valid @RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}