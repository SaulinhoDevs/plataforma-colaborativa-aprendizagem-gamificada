package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.DashboardDataDTO;
import com.aprendizagem.project.dto.QuizResumoDTO;
import com.aprendizagem.project.dto.RankingItemDTO;
import com.aprendizagem.project.dto.UsuarioDTO;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.QuizRepository;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final QuizRepository quizRepository;

    public DashboardController(UsuarioRepository usuarioRepository, QuizRepository quizRepository) {
        this.usuarioRepository = usuarioRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, Principal principal) {
        String userEmail = principal.getName();
        Usuario usuarioLogado = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("Utilizador não encontrado no banco de dados."));

        DashboardDataDTO dashboardData = new DashboardDataDTO();

        // Mapeia os dados do utilizador logado para o DTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuarioLogado.getNome());
        usuarioDTO.setAvatarUrl("https://placehold.co/40x40/9333ea/FFFFFF?text=" + usuarioLogado.getNome().substring(0, 1).toUpperCase());
        dashboardData.setUsuario(usuarioDTO);

        // --- CORREÇÃO PRINCIPAL ---
        // Busca quizzes reais do banco e mapeia-os para DTOs, passando o ID
        List<QuizResumoDTO> todosOsQuizzes = quizRepository.findAll().stream()
                .map(quiz -> new QuizResumoDTO(
                        quiz.getId(), // A passar o ID do quiz para o DTO
                        quiz.getTitulo(),
                        quiz.getCategoria(),
                        0 // Progresso ainda é fixo, será implementado depois
                ))
                .collect(Collectors.toList());

        dashboardData.setNovosQuizzes(todosOsQuizzes);
        dashboardData.setQuizzesEmProgresso(Collections.emptyList()); // Ainda não temos lógica de progresso

        // Dados de exemplo restantes (serão substituídos no futuro)
        dashboardData.setPontosTotais(1250);
        dashboardData.setQuizzesConcluidos(15);
        dashboardData.setPrecisao(88);
        dashboardData.setConquistas(Arrays.asList("🏅", "🌍", "🏆"));
        dashboardData.setRanking(Arrays.asList(
                new RankingItemDTO(1, "Ana", 1850, "https://placehold.co/40x40/22c55e/FFFFFF?text=A", false),
                new RankingItemDTO(2, "Você", 1250, usuarioDTO.getAvatarUrl(), true),
                new RankingItemDTO(3, "Bruno", 980, "https://placehold.co/40x40/3b82f6/FFFFFF?text=B", false)
        ));

        model.addAttribute("dashboardData", dashboardData);
        return "dashboard";
    }
}

