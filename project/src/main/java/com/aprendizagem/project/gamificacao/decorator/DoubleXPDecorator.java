package com.aprendizagem.project.gamificacao.decorator;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import com.aprendizagem.project.gamificacao.strategy.PontuacaoStrategy;

public class DoubleXPDecorator extends PontuacaoDecorator {

    public DoubleXPDecorator(PontuacaoStrategy strategy) {
        super(strategy);
    }

    @Override
    public int calcularPontuacao(ParticipacaoDesafio participacao) {
        int pontosBase = strategy.calcularPontuacao(participacao);
        int total = pontosBase * 2;
        participacao.setPontos(total);
        return total;
    }
}