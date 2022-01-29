package br.com.boasaude.gisa.conveniado.repository;

import br.com.boasaude.gisa.conveniado.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
