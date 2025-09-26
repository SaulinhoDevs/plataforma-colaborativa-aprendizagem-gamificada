package com.aprendizagem.project.service.strategy;

import com.aprendizagem.project.model.UsuarioQuizProgresso;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Segunda implementação concreta do padrão Decorator.
 * Esta classe "decora" uma estratégia de pontuação, adicionando um bónus
 * se o utilizador tiver completado um quiz no dia anterior (streak).
 */
@Component("streakBonus") // Nome do Bean para injeção de dependência
public class StreakBonusDecorator extends PontuacaoDecorator {

    private static final int BONUS_POR_STREAK = 25; // Ex: 25 pontos extra por manter a sequência
    private final UsuarioQuizProgressoRepository progressoRepository;

    /**
     * O construtor recebe a estratégia que será decorada (que, no nosso caso,
     * será a "pontuacaoPorTempo") e o repositório necessário para verificar a streak.
     */
    public StreakBonusDecorator(@Qualifier("pontuacaoPorTempo") PontuacaoStrategy wrapped,
                                UsuarioQuizProgressoRepository progressoRepository) {
        super(wrapped);
        this.progressoRepository = progressoRepository;
    }

    @Override
    public int calcularPontos(UsuarioQuizProgresso progresso) {
        // 1. Calcula a pontuação base (que já pode incluir o bónus por tempo)
        int pontuacaoBase = super.calcularPontos(progresso);

        // 2. Adiciona a nova lógica de bónus por streak
        if (temStreakAtiva(progresso.getUsuario())) {
            System.out.println(">>> Bónus por STREAK aplicado: " + BONUS_POR_STREAK + " pontos!");
            return pontuacaoBase + BONUS_POR_STREAK;
        }

        return pontuacaoBase;
    }

    /**
     * Verifica se o utilizador completou um quiz no dia anterior.
     * @param usuario O utilizador a ser verificado.
     * @return true se houver uma streak ativa, false caso contrário.
     */
    private boolean temStreakAtiva(com.aprendizagem.project.model.Usuario usuario) {
        // A data de ontem
        LocalDate ontem = LocalDate.now().minusDays(1);
        
        // No futuro, criaremos este método no nosso repositório
        // return progressoRepository.existsByUsuarioAndDataConclusao(usuario, ontem);
        
        // Por agora, vamos simular que o utilizador tem uma streak
        return true;
    }
}
