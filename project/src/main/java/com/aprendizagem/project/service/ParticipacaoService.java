package com.aprendizagem.project.service;

import com.aprendizagem.project.desafios.ParticipacaoDesafio;
import com.aprendizagem.project.repository.ParticipacaoDesafioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipacaoService {

    private final ParticipacaoDesafioRepository participacaoRepository;

    public ParticipacaoService(ParticipacaoDesafioRepository participacaoRepository) {
        this.participacaoRepository = participacaoRepository;
    }

    public ParticipacaoDesafio registrarParticipacao(ParticipacaoDesafio participacao) {
        return participacaoRepository.save(participacao);
    }

    public List<ParticipacaoDesafio> rankingPorDesafio(Long desafioId) {
        return participacaoRepository.findRankingByDesafio(desafioId);
    }
}