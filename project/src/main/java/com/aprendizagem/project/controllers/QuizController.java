package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.QuizDataDTO;
import com.aprendizagem.project.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuizController {

    // Adicionado um logger para nos ajudar a depurar
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz/{id}")
    public String iniciarQuiz(@PathVariable Long id, Model model) {
        try {
            logger.info("A tentar carregar o quiz com ID: {}", id);
            QuizDataDTO quizData = quizService.findQuizById(id);
            model.addAttribute("quizData", quizData);
            return "quiz";
        } catch (RuntimeException e) {
            // CORREÇÃO: Adicionado um log de erro para vermos a causa do redirecionamento
            logger.error("Erro ao buscar o quiz com ID: {}. A redirecionar para o dashboard.", id, e);

            // No futuro, podemos adicionar uma mensagem de erro para o utilizador
            // ex: RedirectAttributes redirectAttributes.addFlashAttribute("errorMessage", "O quiz solicitado não foi encontrado.");
            return "redirect:/dashboard";
        }
    }
}

