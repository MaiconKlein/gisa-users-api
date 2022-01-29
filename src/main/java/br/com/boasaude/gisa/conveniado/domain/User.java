package br.com.boasaude.gisa.conveniado.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "gisa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    private String role;

}
