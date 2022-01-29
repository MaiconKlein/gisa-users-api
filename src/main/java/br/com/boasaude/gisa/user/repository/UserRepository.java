package br.com.boasaude.gisa.user.repository;

import br.com.boasaude.gisa.user.domain.GisaUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<GisaUser, Long> {
}
