package br.com.boasaude.gisa.conveniado.service;

import br.com.boasaude.gisa.conveniado.domain.Conveniado;
import br.com.boasaude.gisa.conveniado.domain.Endereco;
import br.com.boasaude.gisa.conveniado.dto.ConveniadoDto;
import br.com.boasaude.gisa.conveniado.repository.ConveniadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConveniadoService {
    private final ConveniadoRepository conveniadoRepository;

    @Transactional(readOnly = true)
    public List<ConveniadoDto> listar() {
        List<ConveniadoDto> conveniadoDtoList = new ArrayList<>();
        conveniadoRepository.findAll().forEach(conveniado ->
                conveniadoDtoList.add(getConveniadoDto(conveniado)));
        return conveniadoDtoList;
    }

    private ConveniadoDto getConveniadoDto(Conveniado conveniado) {
        return ConveniadoDto.builder()
                .cep(conveniado.getEndereco().getCep())
                .cidade(conveniado.getEndereco().getCidade())
                .complemento(conveniado.getEndereco().getComplemento())
                .endereco(conveniado.getEndereco().getEndereco())
                .estado(conveniado.getEndereco().getEstado())
                .cpf(conveniado.getCpf())
                .email(conveniado.getEmail())
                .nome(conveniado.getNome())
                .id(conveniado.getId())
                .build();
    }

    public ConveniadoDto criar(ConveniadoDto conveniadoDto) {
        Conveniado conveniado = Conveniado.builder()
                .cpf(conveniadoDto.getCpf())
                .nome(conveniadoDto.getNome())
                .email(conveniadoDto.getEmail())
                .endereco(Endereco.builder()
                        .endereco(conveniadoDto.getEndereco())
                        .cep(conveniadoDto.getCep())
                        .cidade(conveniadoDto.getCidade())
                        .complemento(conveniadoDto.getComplemento())
                        .estado(conveniadoDto.getEstado())
                        .build())
                .build();

        conveniadoRepository.save(conveniado);

        return getConveniadoDto(conveniado);
    }
}
