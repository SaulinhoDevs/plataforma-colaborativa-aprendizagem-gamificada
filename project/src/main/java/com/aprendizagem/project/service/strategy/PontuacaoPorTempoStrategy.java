package com.aprendizagem.project.service.strategy;

import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.beans.factory.annotation.Qualifier; // ADICIONADO
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Implementação concreta do padrão Decorator.
 */
@Component("pontuacaoPorTempo")
public class PontuacaoPorTempoStrategy extends PontuacaoDecorator {

    private static final long TEMPO_LIMITE_SEGUNDOS = 60;
    private static final int BONUS_POR_RAPIDEZ = 5;

    /**
     * O construtor agora usa @Qualifier para dizer ao Spring exatamente qual bean
     * deve ser injetado, resolvendo a ambiguidade.
     * @param wrapped A estratégia a ser decorada.
     */
    public PontuacaoPorTempoStrategy(@Qualifier("pontuacaoSimples") PontuacaoStrategy wrapped) { // MUDANÇA
        super(wrapped);
    }

    @Override
    public int calcularPontos(UsuarioQuizProgresso progresso) {
        int pontuacaoBase = super.calcularPontos(progresso);

        long tempoGasto = calcularTempoGasto(progresso);
        long tempoLimite = progresso.getTotalPerguntas() * TEMPO_LIMITE_SEGUNDOS;

        if (tempoGasto < tempoLimite) {
            int bonus = progresso.getAcertos() * BONUS_POR_RAPIDEZ;
            System.out.println(">>> Bónus por rapidez aplicado: " + bonus + " pontos!");
            return pontuacaoBase + bonus;
        }

        return pontuacaoBase;
    }

    private long calcularTempoGasto(UsuarioQuizProgresso progresso) {
        // Simulação do tempo de início
        LocalDateTime iniciadoEm = progresso.getConcluidoEm().minusSeconds(70);
        return Duration.between(iniciadoEm, progresso.getConcluidoEm()).getSeconds();
    }
}

