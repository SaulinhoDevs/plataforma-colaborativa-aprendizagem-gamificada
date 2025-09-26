package com.aprendizagem.project.service.strategy;

import com.aprendizagem.project.model.UsuarioQuizProgresso;

/**
 * Classe abstrata para o padrão Decorator.
 * Todos os decoradores de pontuação concretos (ex: bónus por streak, double XP)
 * irão herdar desta classe.
 *
 * Ela implementa a mesma interface que a estratégia base (PontuacaoStrategy)
 * e contém uma referência a outro objeto PontuacaoStrategy, que é o objeto
 * que ela irá "decorar" com funcionalidades adicionais.
 */
public abstract class PontuacaoDecorator implements PontuacaoStrategy {

    // A estratégia de pontuação que está a ser "embrulhada" (wrapped)
    protected final PontuacaoStrategy wrapped;

    protected PontuacaoDecorator(PontuacaoStrategy wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * O método base do decorador. Por padrão, ele simplesmente delega a chamada
     * para a estratégia embrulhada. As subclasses irão sobrescrever este método
     * para adicionar a sua própria lógica de bónus.
     * @param progresso O progresso do utilizador no quiz.
     * @return A pontuação calculada.
     */
    @Override
    public int calcularPontos(UsuarioQuizProgresso progresso) {
        return wrapped.calcularPontos(progresso);
    }
}
