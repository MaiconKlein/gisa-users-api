package br.com.boasaude.gisa.conveniado.service;

import br.com.boasaude.gisa.conveniado.domain.Conveniado;
import br.com.boasaude.gisa.conveniado.dto.ConveniadoDto;
import br.com.boasaude.gisa.conveniado.repository.ConveniadoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConveniadoService {
    private final ConveniadoRepository conveniadoRepository;
    private final ModelMapper modelMapper;

    public ConveniadoDto criar(ConveniadoDto conveniadoDto) {
        Conveniado conveniado = modelMapper.map(conveniadoDto, Conveniado.class);


        conveniadoRepository.save(conveniado);

        return modelMapper.map(conveniado, ConveniadoDto.class);
    }
}
