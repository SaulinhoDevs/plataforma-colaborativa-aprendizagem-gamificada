package com.aprendizagem.project.service;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import com.aprendizagem.project.repository.ParticipacaoDesafioRepository;
import com.aprendizagem.project.gamificacao.model.MedalhaEntity;
import com.aprendizagem.project.repository.MedalhaRepository;
import com.aprendizagem.project.gamificacao.decorator.DoubleXPDecorator;
import com.aprendizagem.project.gamificacao.decorator.StreakBonusDecorator;
import com.aprendizagem.project.gamificacao.strategy.PontuacaoPorPesoStrategy;
import com.aprendizagem.project.gamificacao.strategy.PontuacaoStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipacaoService {

    private final ParticipacaoDesafioRepository participacaoRepository;
    private final MedalhaRepository medalhaRepository;

    public ParticipacaoService(ParticipacaoDesafioRepository participacaoRepository,
                               MedalhaRepository medalhaRepository) {
        this.participacaoRepository = participacaoRepository;
        this.medalhaRepository = medalhaRepository;
    }

    public ParticipacaoDesafio registrarParticipacao(ParticipacaoDesafio participacao) {
        // Estratégia base
        PontuacaoStrategy strategy = new PontuacaoPorPesoStrategy();

        // Decorators
        strategy = new StreakBonusDecorator(strategy);
        strategy = new DoubleXPDecorator(strategy);

        strategy.calcularPontuacao(participacao);

        return participacaoRepository.save(participacao);
    }

    //Ranking de um desafio específico + atribuição de medalhas
    public List<ParticipacaoDesafio> rankingPorDesafio(Long desafioId) {
        List<ParticipacaoDesafio> ranking = participacaoRepository.findRankingByDesafio(desafioId);

        if (!ranking.isEmpty()) {
            atribuirMedalhas(ranking);
        }

        return ranking;
    }

    //Ranking geral (todos os desafios) + atribuição de taças
    public List<ParticipacaoDesafio> rankingGeral() {
        List<ParticipacaoDesafio> ranking = participacaoRepository.findAll();

        // ordena pelo total de pontos
        ranking.sort((a, b) -> Integer.compare(b.getPontos(), a.getPontos()));

        if (!ranking.isEmpty()) {
            atribuirTacas(ranking);
        }

        return ranking;
    }

    private void atribuirMedalhas(List<ParticipacaoDesafio> ranking) {
        if (ranking.size() > 0) salvarMedalha("MEDALHA", "Medalha de Ouro", 30, ranking.get(0));
        if (ranking.size() > 1) salvarMedalha("MEDALHA", "Medalha de Prata", 20, ranking.get(1));
        if (ranking.size() > 2) salvarMedalha("MEDALHA", "Medalha de Bronze", 10, ranking.get(2));
    }

    private void atribuirTacas(List<ParticipacaoDesafio> ranking) {
        if (ranking.size() > 0) salvarMedalha("TACA", "Taça de Ouro", 50, ranking.get(0));
        if (ranking.size() > 1) salvarMedalha("TACA", "Taça de Prata", 40, ranking.get(1));
        if (ranking.size() > 2) salvarMedalha("TACA", "Taça de Bronze", 30, ranking.get(2));
    }

    private void salvarMedalha(String tipo, String nome, int pontos, ParticipacaoDesafio participacao) {
        MedalhaEntity medalha = new MedalhaEntity();
        medalha.setTipo(tipo);
        medalha.setNome(nome);
        medalha.setPontos(pontos);
        medalha.setUsuario(participacao.getAluno());

        medalhaRepository.save(medalha);
    }
}