package com.aprendizagem.project.controllers;

import com.aprendizagem.project.model.*;
import com.aprendizagem.project.repository.QuizRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CriarQuizController {

    private final QuizRepository quizRepository;

    public CriarQuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping("/criar-quiz")
    public String mostrarPaginaCriacao() {
        return "criar-quiz";
    }

    /**
     * ADICIONADO: Processamento básico do formulário de criação de quiz
     * Cria um quiz simples com dados fornecidos
     */
    @PostMapping("/quiz/criar")
    public String criarQuiz(@RequestParam String titulo,
                           @RequestParam String descricao,
                           @RequestParam String categoria,
                           @RequestParam(defaultValue = "NORMAL") String tipoQuiz) {
        try {
            // Cria quiz básico
            Quiz novoQuiz = new Quiz(titulo, descricao, categoria);
            
            // Adiciona perguntas de exemplo (para não quebrar o sistema)
            Pergunta pergunta1 = new Pergunta("Qual é a capital do Brasil?");
            pergunta1.addResposta(new Resposta("Brasília", true));
            pergunta1.addResposta(new Resposta("São Paulo", false));
            pergunta1.addResposta(new Resposta("Rio de Janeiro", false));
            pergunta1.addResposta(new Resposta("Salvador", false));
            
            Pergunta pergunta2 = new Pergunta("Quantos estados tem o Brasil?");
            pergunta2.addResposta(new Resposta("26", false));
            pergunta2.addResposta(new Resposta("27", true));
            pergunta2.addResposta(new Resposta("25", false));
            pergunta2.addResposta(new Resposta("28", false));
            
            novoQuiz.addPergunta(pergunta1);
            novoQuiz.addPergunta(pergunta2);
            
            quizRepository.save(novoQuiz);
            
            return "redirect:/gestao-quizzes?success=Quiz criado com sucesso!";
        } catch (Exception e) {
            return "redirect:/criar-quiz?error=Erro ao criar quiz: " + e.getMessage();
        }
    }
}