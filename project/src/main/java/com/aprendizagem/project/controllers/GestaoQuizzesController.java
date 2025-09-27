package com.aprendizagem.project.controllers;

import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.Professor;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.QuizRepository;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
public class GestaoQuizzesController {

    private final QuizRepository quizRepository;
    private final UsuarioRepository usuarioRepository;

    public GestaoQuizzesController(QuizRepository quizRepository, UsuarioRepository usuarioRepository) {
        this.quizRepository = quizRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/gestao-quizzes")
    public String mostrarPaginaGestao(Model model, Principal principal) {
        List<Quiz> quizzes = quizRepository.findAll();
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("isProfessor", isProfessor(principal));
        return "gestao-quizzes";
    }

    @PostMapping("/gestao-quizzes/excluir/{id}")
    public String excluirQuiz(@PathVariable("id") Long id, Principal principal) {
        // Pode-se adicionar verificação de permissão aqui
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
        }
        return "redirect:/gestao-quizzes";
    }

    private boolean isProfessor(Principal principal) {
        if (principal == null) return false;
        return usuarioRepository.findByEmail(principal.getName()).map(u -> u instanceof Professor).orElse(false);
    }
}
