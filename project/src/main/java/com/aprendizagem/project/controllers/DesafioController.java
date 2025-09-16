package com.aprendizagem.project.controllers;

import com.aprendizagem.project.gamificacao.model.Desafio;
import com.aprendizagem.project.service.DesafioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/desafios")
public class DesafioController {

    private final DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @PostMapping
    public ResponseEntity<Desafio> criar(@RequestBody Desafio desafio) {
        return ResponseEntity.ok(desafioService.criarDesafio(desafio));
    }

    @GetMapping
    public ResponseEntity<List<Desafio>> listar() {
        return ResponseEntity.ok(desafioService.listarDesafios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desafio> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(desafioService.buscarPorId(id));
    }
}