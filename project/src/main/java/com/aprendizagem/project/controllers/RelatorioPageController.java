package com.aprendizagem.project.controllers;

import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import com.aprendizagem.project.repository.QuizRepository;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RelatorioPageController {

    private final UsuarioQuizProgressoRepository progressoRepository;
    private final QuizRepository quizRepository;

    public RelatorioPageController(UsuarioQuizProgressoRepository progressoRepository, QuizRepository quizRepository) {
        this.progressoRepository = progressoRepository;
        this.quizRepository = quizRepository;
    }

    /**
     * Exibe a página de relatório para um quiz específico.
     * @param quizId O ID do quiz.
     * @param model O modelo para passar dados para a view.
     * @return O nome do template "relatorio-quiz".
     */
    @GetMapping("/relatorio/quiz/{quizId}")
    public String mostrarRelatorioQuiz(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz não encontrado"));

        List<UsuarioQuizProgresso> progressos = progressoRepository.findByQuizId(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("progressos", progressos);

        return "relatorio-quiz";
    }
}
