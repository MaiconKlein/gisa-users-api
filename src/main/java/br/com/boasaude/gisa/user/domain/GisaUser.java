package br.com.boasaude.gisa.user.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "gisa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GisaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    private String areaAtuacao;

    private Long telefone;

}
