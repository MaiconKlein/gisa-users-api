package br.com.boasaude.gisa.conveniado.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Validated
public class ConveniadoDto {
    @NotEmpty
    private String nome;
    @NotEmpty
    private String cpf;
    @NotEmpty
    private String email;
    @NotEmpty
    private String logradouro;
    @NotEmpty
    private String numero;
    private String complemento;
    @NotEmpty
    private String cidade;
    @NotEmpty
    private String estado;
    @NotEmpty
    private String cep;
}
