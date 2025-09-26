package com.aprendizagem.project.service.strategy;

import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.stereotype.Component;

/**
 * Implementação da Strategy para um cálculo de pontuação simples.
 * Cada resposta correta vale 10 pontos.
 */
@Component("pontuacaoSimples") // Damos um nome ao nosso Bean do Spring
public class PontuacaoSimplesStrategy implements PontuacaoStrategy {

    private static final int PONTOS_POR_ACERTO = 10;

    @Override
    public int calcularPontos(UsuarioQuizProgresso progresso) {
        if (progresso == null || progresso.getAcertos() == null) {
            return 0;
        }
        return progresso.getAcertos() * PONTOS_POR_ACERTO;
    }
}
