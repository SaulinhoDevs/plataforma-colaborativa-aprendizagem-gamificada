package com.aprendizagem.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestaoQuizzesController {

    // Futuramente, injetaremos o QuizRepository para buscar os quizzes do professor.

    /**
     * Mapeia a rota /gestao-quizzes para exibir a página de gestão de quizzes.
     * @return O nome do template "gestao-quizzes".
     */
    @GetMapping("/gestao-quizzes")
    public String mostrarPaginaGestao() {
        // Por agora, apenas exibimos a página. A lógica para carregar os dados
        // será adicionada depois.
        return "gestao-quizzes";
    }
}
