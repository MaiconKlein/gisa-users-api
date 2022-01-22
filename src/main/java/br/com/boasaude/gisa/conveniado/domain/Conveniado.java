package br.com.boasaude.gisa.conveniado.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "conveniado", schema = "gisa_conveniado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Conveniado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long conveniadoId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
