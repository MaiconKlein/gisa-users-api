package br.com.boasaude.gisa.conveniado.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "endereco", schema = "gisa_conveniado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            mappedBy = "endereco")
    private Conveniado conveniado;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    @Column
    private String complemento;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cep;
}
