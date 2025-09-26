package com.aprendizagem.project.service.strategy;

import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Implementação concreta do padrão Decorator.
 * Esta classe "decora" uma estratégia de pontuação existente, adicionando
 * um bónus de pontos se o quiz for concluído rapidamente.
 */
@Component("pontuacaoPorTempo") // Nome do Bean para injeção de dependência
public class PontuacaoPorTempoStrategy extends PontuacaoDecorator {

    private static final long TEMPO_LIMITE_SEGUNDOS = 60; // Ex: 1 minuto por pergunta
    private static final int BONUS_POR_RAPIDEZ = 5; // Ex: 5 pontos de bónus por pergunta

    /**
     * O construtor recebe a estratégia de pontuação base que será decorada.
     * O Spring irá injetar aqui a nossa "pontuacaoSimples".
     * @param wrapped A estratégia a ser decorada.
     */
    public PontuacaoPorTempoStrategy(PontuacaoStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public int calcularPontos(UsuarioQuizProgresso progresso) {
        // 1. Calcula a pontuação base chamando o método da estratégia embrulhada
        int pontuacaoBase = super.calcularPontos(progresso);

        // 2. Adiciona a nova lógica de bónus
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
        // Para este exemplo, vamos simular o tempo de início.
        // Numa implementação real, guardaríamos o 'iniciadoEm' no progresso.
        LocalDateTime iniciadoEm = progresso.getConcluidoEm().minusSeconds(70); // Simulação
        return Duration.between(iniciadoEm, progresso.getConcluidoEm()).getSeconds();
    }
}
