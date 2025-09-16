package com.aprendizagem.project.controllers;


import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import com.aprendizagem.project.service.ParticipacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participacoes")
public class ParticipacaoController {

    private final ParticipacaoService participacaoService;

    public ParticipacaoController(ParticipacaoService participacaoService) {
        this.participacaoService = participacaoService;
    }

    @PostMapping
    public ResponseEntity<ParticipacaoDesafio> registrar(@RequestBody ParticipacaoDesafio participacao) {
        return ResponseEntity.ok(participacaoService.registrarParticipacao(participacao));
    }


    @GetMapping("/ranking/{desafioId}")
    public ResponseEntity<List<ParticipacaoDesafio>> rankingDesafio(@PathVariable Long desafioId) {
        return ResponseEntity.ok(participacaoService.rankingPorDesafio(desafioId));
    }

    @GetMapping("/ranking-geral")
    public ResponseEntity<List<ParticipacaoDesafio>> rankingGeral() {
        return ResponseEntity.ok(participacaoService.rankingGeral());
    }
}