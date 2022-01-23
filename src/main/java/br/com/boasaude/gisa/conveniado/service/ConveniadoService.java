package br.com.boasaude.gisa.conveniado.service;

import br.com.boasaude.gisa.conveniado.domain.Conveniado;
import br.com.boasaude.gisa.conveniado.domain.Endereco;
import br.com.boasaude.gisa.conveniado.dto.ConveniadoDto;
import br.com.boasaude.gisa.conveniado.repository.ConveniadoRepository;
import com.auth0.exception.Auth0Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConveniadoService {
    private final ConveniadoRepository conveniadoRepository;
    private final EnderecoService enderecoService;
    private final ManagementAPIService managementAPIService;

    @Transactional(readOnly = true)
    public List<ConveniadoDto> listar() {
        List<ConveniadoDto> conveniadoDtoList = new ArrayList<>();
        conveniadoRepository.findAll().forEach(conveniado ->
        {
            ConveniadoDto conveniadoDto = getConveniadoDto(conveniado);
            conveniadoDtoList.add(conveniadoDto);
        });
        return conveniadoDtoList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ConveniadoDto criar(ConveniadoDto conveniadoDto, String token) throws Auth0Exception {
        Endereco endereco = enderecoService.getEndereco(conveniadoDto);
        Conveniado conveniado = getConveniado(conveniadoDto, endereco);
        conveniadoRepository.save(conveniado);

        managementAPIService.atualizarUserRole(token, conveniado.getEmail());
        return getConveniadoDto(conveniado);
    }

    public void delete(Long id) {
        conveniadoRepository.deleteById(id);
    }

    @Transactional
    public ConveniadoDto atualizar(ConveniadoDto conveniadoDto) {
        Optional<Conveniado> conveniadoOptional = conveniadoRepository.findById(conveniadoDto.getId());
        if (conveniadoOptional.isPresent()) {
            Conveniado conveniado = conveniadoOptional.get();

            conveniado.setCpf(conveniadoDto.getCpf());
            conveniado.setEmail(conveniadoDto.getEmail());
            conveniado.setNome(conveniadoDto.getNome());

            enderecoService.atualizar(conveniadoDto, conveniado.getEndereco());

            return getConveniadoDto(conveniadoRepository.save(conveniado));
        }
        throw new EntityNotFoundException("Conveniado não cadastrado");
    }

    private Conveniado getConveniado(ConveniadoDto conveniadoDto, Endereco endereco) {
        Conveniado conveniado = Conveniado.builder()
                .cpf(conveniadoDto.getCpf())
                .nome(conveniadoDto.getNome())
                .email(conveniadoDto.getEmail())
                .conveniadoId(conveniadoDto.getId())
                .endereco(endereco)
                .build();
        return conveniado;
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
                .id(conveniado.getConveniadoId())
                .build();
    }


}
