package com.aprendizagem.project.service.strategy;

import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Segunda implementação concreta do padrão Decorator.
 */
@Component("streakBonus")
public class StreakBonusDecorator extends PontuacaoDecorator {

    private static final int BONUS_POR_STREAK = 25;
    private final UsuarioQuizProgressoRepository progressoRepository;

    public StreakBonusDecorator(@Qualifier("pontuacaoPorTempo") PontuacaoStrategy wrapped,
                                UsuarioQuizProgressoRepository progressoRepository) {
        super(wrapped);
        this.progressoRepository = progressoRepository;
    }

    @Override
    public int calcularPontos(UsuarioQuizProgresso progresso) {
        int pontuacaoBase = super.calcularPontos(progresso);

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
    private boolean temStreakAtiva(Usuario usuario) {
        // CORREÇÃO: Lógica real para verificar a streak
        LocalDate ontem = LocalDate.now().minusDays(1);
        LocalDateTime inicioDeOntem = ontem.atStartOfDay();
        LocalDateTime fimDeOntem = ontem.atTime(23, 59, 59);

        // Este método precisa de ser adicionado ao repositório
        return progressoRepository.existsByUsuarioAndConcluidoEmBetween(usuario, inicioDeOntem, fimDeOntem);
    }
}

