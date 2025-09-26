package com.aprendizagem.project.service.strategy;

import com.aprendizagem.project.model.UsuarioQuizProgresso;

/**
 * Interface Strategy para diferentes algoritmos de cálculo de pontuação.
 * Cada implementação desta interface definirá uma forma diferente de pontuar um quiz.
 */
public interface PontuacaoStrategy {
    /**
     * Calcula a pontuação final com base no progresso de um utilizador num quiz.
     * @param progresso O objeto contendo os dados da tentativa do utilizador.
     * @return A pontuação calculada.
     */
    int calcularPontos(UsuarioQuizProgresso progresso);
}
