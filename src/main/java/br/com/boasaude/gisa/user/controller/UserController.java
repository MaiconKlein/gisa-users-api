package br.com.boasaude.gisa.user.controller;

import br.com.boasaude.gisa.user.dto.UserDto;
import br.com.boasaude.gisa.user.service.UserService;
import com.auth0.exception.Auth0Exception;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar Usuários",
            description = "Retorna uma lista de usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de usuário"),
            @ApiResponse(responseCode = "403",
                    description = "Você não tem permissão para acessar este recurso",
                    content = @Content(examples = @ExampleObject))})
    @GetMapping(produces = "application/json")
    public ResponseEntity<UserDto> listar() {
        return new ResponseEntity(userService.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Criar Usuário",
            description = "Executa a criação de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o usuário criado"),
            @ApiResponse(responseCode = "403",
                    description = "Você não tem permissão para acessar este recurso",
                    content = @Content(examples = @ExampleObject))})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> criar(@Valid @RequestBody UserDto userDto,
                                         @NotEmpty @RequestHeader String role) throws Auth0Exception {
        UserDto dto = userService.criar(userDto, role);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar Usuário",
            description = "Executa a atualização de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Retorna o usuário atualizado"),
            @ApiResponse(responseCode = "403",
                    description = "Você não tem permissão para acessar este recurso",
                    content = @Content(examples = @ExampleObject))})
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> atualizar(@Valid @RequestBody UserDto userDto,
                                             @NotEmpty @RequestHeader String role) throws Auth0Exception {
        return new ResponseEntity(userService.atualizar(userDto, role), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Excluir Usuário",
            description = "Executa a exclusão de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403",
                    description = "Você não tem permissão para acessar este recurso",
                    content = @Content(examples = @ExampleObject))})
    @DeleteMapping
    public ResponseEntity<Void> deletar(@Valid @RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}