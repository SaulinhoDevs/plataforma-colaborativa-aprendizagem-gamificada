package com.aprendizagem.project.service.observer;

/**
 * Interface Observer para reagir a eventos de conclusão de quiz.
 */
public interface QuizCompletionObserver {
    /**
     * Método chamado pelo "Subject" quando um quiz é concluído.
     * @param event Os dados do evento.
     */
    void update(QuizCompletionEvent event);
}
