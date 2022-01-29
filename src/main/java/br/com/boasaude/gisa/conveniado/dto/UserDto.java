package br.com.boasaude.gisa.conveniado.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Validated
public class UserDto {
    private Long id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String cpf;
    @NotEmpty
    private String email;
    @NotEmpty
    private String endereco;
    private String complemento;
    @NotEmpty
    private String cidade;
    @NotEmpty
    private String estado;
    @NotEmpty
    private String cep;
}
