package com.aprendizagem.project.gamificacao.strategy;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;

public interface PontuacaoStrategy {
    int calcularPontuacao(ParticipacaoDesafio participacao);
}