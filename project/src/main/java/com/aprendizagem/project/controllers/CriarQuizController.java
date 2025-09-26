package com.aprendizagem.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CriarQuizController {

    /**
     * Mapeia a rota /criar-quiz para exibir a página de criação de quizzes.
     * @return O nome do template "criar-quiz".
     */
    @GetMapping("/criar-quiz")
    public String mostrarPaginaCriacao() {
        // Por agora, apenas exibimos a página. A lógica para processar o
        // formulário de criação será adicionada depois.
        return "criar-quiz";
    }
}
