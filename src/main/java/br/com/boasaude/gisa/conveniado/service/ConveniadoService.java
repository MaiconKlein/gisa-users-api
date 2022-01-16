package br.com.boasaude.gisa.conveniado.service;

import br.com.boasaude.gisa.conveniado.domain.Conveniado;
import br.com.boasaude.gisa.conveniado.dto.ConveniadoDto;
import br.com.boasaude.gisa.conveniado.repository.ConveniadoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ConveniadoService {
    private final ConveniadoRepository conveniadoRepository;
    private final ModelMapper modelMapper;

    public List<ConveniadoDto> listar() {
        return Stream.of(conveniadoRepository.findAll())
                .map(conveniado -> modelMapper.map(conveniado, ConveniadoDto.class))
                .collect(Collectors.toList());
    }

    public ConveniadoDto criar(ConveniadoDto conveniadoDto) {
        Conveniado conveniado = modelMapper.map(conveniadoDto, Conveniado.class);

        conveniadoRepository.save(conveniado);

        return modelMapper.map(conveniado, ConveniadoDto.class);
    }
}
