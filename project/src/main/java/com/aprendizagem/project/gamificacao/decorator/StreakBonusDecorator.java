package com.aprendizagem.project.gamificacao.decorator;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import com.aprendizagem.project.gamificacao.strategy.PontuacaoStrategy;

public class StreakBonusDecorator extends PontuacaoDecorator {

    public StreakBonusDecorator(PontuacaoStrategy strategy) {
        super(strategy);
    }

    @Override
    public int calcularPontuacao(ParticipacaoDesafio participacao) {
        int pontosBase = strategy.calcularPontuacao(participacao);
        int bonus = participacao.getMaiorSequenciaAcertos() * 2; // +2 pontos por streak
        int total = pontosBase + bonus;
        participacao.setPontos(total);
        return total;
    }
}