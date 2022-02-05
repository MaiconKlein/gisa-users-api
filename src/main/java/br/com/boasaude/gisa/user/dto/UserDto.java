package br.com.boasaude.gisa.user.dto;

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

    private String areaAtuacao;

    private Long telefone;

}
