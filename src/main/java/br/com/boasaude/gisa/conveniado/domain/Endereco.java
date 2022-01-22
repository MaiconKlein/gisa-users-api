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
    private Long enderecoId;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "endereco")
    private List<Conveniado> conveniados;

    @Column(nullable = false)
    private String endereco;

    @Column
    private String complemento;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cep;
}
