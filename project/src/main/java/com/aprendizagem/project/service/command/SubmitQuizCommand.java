package com.aprendizagem.project.service.command;

import com.aprendizagem.project.dto.QuizResultadoDTO;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.service.QuizService;

/**
 * Implementação concreta do padrão Command.
 * Encapsula a ação de submeter o resultado de um quiz.
 */
public class SubmitQuizCommand implements Command {

    private final QuizService quizService;
    private final QuizResultadoDTO resultadoDTO;
    private final Usuario usuario;

    public SubmitQuizCommand(QuizService quizService, QuizResultadoDTO resultadoDTO, Usuario usuario) {
        this.quizService = quizService;
        this.resultadoDTO = resultadoDTO;
        this.usuario = usuario;
    }

    /**
     * Executa a ação, delegando a chamada para o QuizService.
     */
    @Override
    public void execute() {
        quizService.processarResultadoQuiz(resultadoDTO, usuario);
    }

    /**
     * Desfaz a ação, delegando a chamada para um novo método no QuizService.
     * Isto mantém a lógica de negócio no service, onde ela pertence.
     */
    @Override
    public void undo() {
        // O QuizService irá conter a lógica para encontrar e apagar o registo de progresso
        quizService.reverterResultadoQuiz(resultadoDTO.getQuizId(), usuario);
    }
}

