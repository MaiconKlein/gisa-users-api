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
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Endereco endereco;
}
