package com.aprendizagem.project.controllers;

import com.aprendizagem.project.model.MuralPost;
import com.aprendizagem.project.repository.MuralPostRepository;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
public class MuralController {

    private final MuralPostRepository muralRepo;
    private final UsuarioRepository usuarioRepository;

    public MuralController(MuralPostRepository muralRepo, UsuarioRepository usuarioRepository) {
        this.muralRepo = muralRepo;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/mural")
    public String mostrarPaginaMural(Model model, Principal principal) {
        List<MuralPost> posts = muralRepo.findTop50ByOrderByCriadoEmDesc();
        model.addAttribute("posts", posts);

        if (principal != null) {
            usuarioRepository.findByEmail(principal.getName()).ifPresent(u -> model.addAttribute("usuarioNome", u.getNome()));
        } else {
            model.addAttribute("usuarioNome", "Visitante");
        }

        return "mural";
    }

    @PostMapping("/mural/postar")
    public String postarMensagem(@RequestParam("conteudo") String conteudo,
                                 Principal principal) {
        String autor = "Visitante";
        if (principal != null) {
            autor = usuarioRepository.findByEmail(principal.getName()).map(Usuario::getNome).orElse(autor);
        }
        if (conteudo != null && !conteudo.trim().isEmpty()) {
            MuralPost p = new MuralPost(autor, conteudo.trim());
            muralRepo.save(p);
        }
        return "redirect:/mural";
    }
}
