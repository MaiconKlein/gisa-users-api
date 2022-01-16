package br.com.boasaude.gisa.conveniado.controller;

import br.com.boasaude.gisa.conveniado.dto.ConveniadoDto;
import br.com.boasaude.gisa.conveniado.service.ConveniadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/public/v1/conveniados")
@RequiredArgsConstructor
public class ConveniadoController {
    private final ConveniadoService conveniadoService;

    @GetMapping
    public ResponseEntity<String> find() {
        return new ResponseEntity(conveniadoService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConveniadoDto> criar(@Valid @RequestBody ConveniadoDto conveniadoDto) {
        return new ResponseEntity(conveniadoService.criar(conveniadoDto), HttpStatus.CREATED);
    }

}