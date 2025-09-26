package com.aprendizagem.project.service.observer;

import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import lombok.Getter;

/**
 * Representa os dados de um evento de conclusão de quiz.
 * Este objeto é passado do "Subject" (QuizService) para os "Observers".
 */
@Getter
public class QuizCompletionEvent {
    private final Usuario usuario;
    private final UsuarioQuizProgresso progresso;

    public QuizCompletionEvent(Usuario usuario, UsuarioQuizProgresso progresso) {
        this.usuario = usuario;
        this.progresso = progresso;
    }
}
