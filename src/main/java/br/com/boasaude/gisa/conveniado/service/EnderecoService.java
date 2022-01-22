package br.com.boasaude.gisa.conveniado.service;

import br.com.boasaude.gisa.conveniado.domain.Endereco;
import br.com.boasaude.gisa.conveniado.dto.ConveniadoDto;
import br.com.boasaude.gisa.conveniado.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    @Transactional
    public Endereco criar(ConveniadoDto conveniadoDto) {
        return enderecoRepository.save(getEndereco(conveniadoDto));
    }

    @Transactional
    public Endereco atualizar(ConveniadoDto conveniadoDto, Endereco endereco) {
        if (endereco != null) {
            endereco.setCep(conveniadoDto.getCep());
            endereco.setCidade(conveniadoDto.getCidade());
            endereco.setComplemento(conveniadoDto.getComplemento());
            endereco.setEndereco(conveniadoDto.getEndereco());
            endereco.setEstado(conveniadoDto.getEstado());
            return enderecoRepository.save(endereco);
        }
        throw new EntityNotFoundException("Endereço não encontrado.");
    }

    public Endereco getEndereco(ConveniadoDto conveniadoDto) {
        return Endereco.builder()
                .endereco(conveniadoDto.getEndereco())
                .cep(conveniadoDto.getCep())
                .cidade(conveniadoDto.getCidade())
                .complemento(conveniadoDto.getComplemento())
                .estado(conveniadoDto.getEstado())
                .build();
    }


}
