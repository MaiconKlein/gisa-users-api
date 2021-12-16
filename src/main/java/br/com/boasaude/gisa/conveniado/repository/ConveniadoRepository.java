package br.com.boasaude.gisa.conveniado.repository;

import br.com.boasaude.gisa.conveniado.domain.Conveniado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConveniadoRepository extends CrudRepository<Conveniado, Long> {
}
