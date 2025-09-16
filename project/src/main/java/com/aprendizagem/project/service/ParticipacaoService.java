package com.aprendizagem.project.service;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import com.aprendizagem.project.gamificacao.strategy.PontuacaoPorPesoStrategy;
import com.aprendizagem.project.repository.ParticipacaoDesafioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipacaoService {

    private final ParticipacaoDesafioRepository participacaoRepository;
    private final PontuacaoPorPesoStrategy strategy = new PontuacaoPorPesoStrategy();

    public ParticipacaoService(ParticipacaoDesafioRepository participacaoRepository) {
        this.participacaoRepository = participacaoRepository;
    }

    public ParticipacaoDesafio registrarParticipacao(ParticipacaoDesafio participacao) {
        // calcula pontos com a estratégia configurada
        strategy.calcularPontuacao(participacao);
        return participacaoRepository.save(participacao);
    }

    public List<ParticipacaoDesafio> rankingPorDesafio(Long desafioId) {
        return participacaoRepository.findRankingByDesafio(desafioId);
    }
}