package br.com.boasaude.gisa.conveniado.repository;

import br.com.boasaude.gisa.conveniado.domain.Conveniado;
import br.com.boasaude.gisa.conveniado.domain.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {
}
