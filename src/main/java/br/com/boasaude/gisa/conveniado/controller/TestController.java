package br.com.boasaude.gisa.conveniado.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> find() {
        return ResponseEntity.ok("Hello Public!");
    }

}