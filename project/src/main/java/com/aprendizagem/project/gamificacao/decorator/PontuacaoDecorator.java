package com.aprendizagem.project.gamificacao.decorator;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import com.aprendizagem.project.gamificacao.strategy.PontuacaoStrategy;

public abstract class PontuacaoDecorator implements PontuacaoStrategy {

    protected final PontuacaoStrategy strategy;

    public PontuacaoDecorator(PontuacaoStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public abstract int calcularPontuacao(ParticipacaoDesafio participacao);
}