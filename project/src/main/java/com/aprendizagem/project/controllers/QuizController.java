package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.QuizDataDTO;
import com.aprendizagem.project.dto.QuizResultadoDTO;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.UsuarioRepository;
import com.aprendizagem.project.service.QuizService;
import com.aprendizagem.project.service.command.CommandInvoker;
import com.aprendizagem.project.service.command.SubmitQuizCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizService quizService;
    private final CommandInvoker commandInvoker; // ADICIONADO: O Invocador
    private final UsuarioRepository usuarioRepository;

    public QuizController(QuizService quizService,
                          CommandInvoker commandInvoker, // ADICIONADO
                          UsuarioRepository usuarioRepository) {
        this.quizService = quizService;
        this.commandInvoker = commandInvoker; // ADICIONADO
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/quiz/{id}")
    public String iniciarQuiz(@PathVariable Long id, Model model) {
        try {
            logger.info("A tentar carregar o quiz com ID: {}", id);
            QuizDataDTO quizData = quizService.findQuizById(id);
            model.addAttribute("quizData", quizData);
            return "quiz";
        } catch (RuntimeException e) {
            logger.error("Erro ao buscar o quiz com ID: {}. A redirecionar para o dashboard.", id, e);
            return "redirect:/dashboard";
        }
    }

    /**
     * ATUALIZADO: Este endpoint agora usa o padrão Command.
     */
    @PostMapping("/quiz/submit")
    public ResponseEntity<Void> submitResultado(@RequestBody QuizResultadoDTO resultadoDTO, Principal principal) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Utilizador não autenticado encontrado."));

            // 1. Cria o objeto de comando com os dados necessários
            SubmitQuizCommand command = new SubmitQuizCommand(quizService, resultadoDTO, usuario);

            // 2. Entrega o comando ao invocador para execução
            commandInvoker.executeCommand(command);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao submeter o resultado do quiz.", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

