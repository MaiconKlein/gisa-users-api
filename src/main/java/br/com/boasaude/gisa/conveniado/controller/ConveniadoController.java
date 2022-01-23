package br.com.boasaude.gisa.conveniado.controller;

import br.com.boasaude.gisa.conveniado.dto.ConveniadoDto;
import br.com.boasaude.gisa.conveniado.service.ConveniadoService;
import br.com.boasaude.gisa.conveniado.service.ManagementAPIService;
import com.auth0.exception.Auth0Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/private/conveniados")
@RequiredArgsConstructor
public class ConveniadoController {
    private final ConveniadoService conveniadoService;

    @GetMapping
    public ResponseEntity<String> listar() {
        return new ResponseEntity(conveniadoService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConveniadoDto> criar(@Valid @RequestBody ConveniadoDto conveniadoDto, @RequestHeader (name="Authorization") String token) throws Auth0Exception {
        ConveniadoDto dto = conveniadoService.criar(conveniadoDto, token);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ConveniadoDto> atualizar(@Valid @RequestBody ConveniadoDto conveniadoDto) {
        return new ResponseEntity(conveniadoService.atualizar(conveniadoDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@Valid @RequestParam Long id) {
        conveniadoService.delete(id);
        return ResponseEntity.ok().build();
    }

}