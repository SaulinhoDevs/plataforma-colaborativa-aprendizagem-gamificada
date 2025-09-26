package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.PerfilDTO;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Arrays; // ADICIONADO: Importação que faltava

@Controller
public class PerfilController {

    private final UsuarioRepository usuarioRepository;
    // Futuramente, injetaremos outros repositórios para buscar dados de progresso e conquistas

    public PerfilController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));

        // Mapeia os dados da entidade para o DTO
        PerfilDTO perfilDTO = new PerfilDTO();
        perfilDTO.setNome(usuario.getNome());
        perfilDTO.setEmail(usuario.getEmail());
        perfilDTO.setAvatarUrl("https://placehold.co/120x120/9333ea/FFFFFF?text=" + usuario.getNome().substring(0, 1).toUpperCase());

        // DADOS DE EXEMPLO (serão substituídos pela lógica de negócio real)
        perfilDTO.setNivel(5);
        perfilDTO.setPontosTotais(1250);
        perfilDTO.setQuizzesConcluidos(15);
        perfilDTO.setPrecisaoMedia(88);
        perfilDTO.setConquistasIcones(Arrays.asList("🏅", "🌍", "🏆"));

        model.addAttribute("perfilData", perfilDTO);

        return "perfil"; // Retorna o template perfil.html
    }
}

