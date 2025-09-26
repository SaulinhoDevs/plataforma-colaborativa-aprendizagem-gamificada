package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.DashboardDataDTO;
import com.aprendizagem.project.dto.QuizResumoDTO;
import com.aprendizagem.project.dto.RankingItemDTO;
import com.aprendizagem.project.dto.UsuarioDTO;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.QuizRepository;
import com.aprendizagem.project.repository.UsuarioRepository;
import com.aprendizagem.project.service.integration.ExternalRankingAdapter; // ADICIONADO
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final QuizRepository quizRepository;
    private final ExternalRankingAdapter rankingAdapter; // ADICIONADO

    public DashboardController(UsuarioRepository usuarioRepository,
                               QuizRepository quizRepository,
                               ExternalRankingAdapter rankingAdapter) { // ADICIONADO
        this.usuarioRepository = usuarioRepository;
        this.quizRepository = quizRepository;
        this.rankingAdapter = rankingAdapter; // ADICIONADO
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, Principal principal) {
        String userEmail = principal.getName();
        Usuario usuarioLogado = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("Utilizador não encontrado no banco de dados."));

        DashboardDataDTO dashboardData = new DashboardDataDTO();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuarioLogado.getNome());
        usuarioDTO.setAvatarUrl("https://placehold.co/40x40/9333ea/FFFFFF?text=" + usuarioLogado.getNome().substring(0, 1).toUpperCase());
        dashboardData.setUsuario(usuarioDTO);

        List<QuizResumoDTO> todosOsQuizzes = quizRepository.findAll().stream()
                .map(quiz -> new QuizResumoDTO(quiz.getId(), quiz.getTitulo(), quiz.getCategoria(), 0))
                .collect(Collectors.toList());

        dashboardData.setNovosQuizzes(todosOsQuizzes);
        dashboardData.setQuizzesEmProgresso(Collections.emptyList());
        dashboardData.setPontosTotais(1250);
        dashboardData.setQuizzesConcluidos(15);
        dashboardData.setPrecisao(88);
        dashboardData.setConquistas(Arrays.asList("🏅", "🌍", "🏆"));

        // --- LÓGICA DO ADAPTER ---
        // 1. Ranking interno (com o nosso utilizador)
        List<RankingItemDTO> rankingInterno = new ArrayList<>(Arrays.asList(
                new RankingItemDTO(0, "Ana", 1850, "https://placehold.co/40x40/22c55e/FFFFFF?text=A", false),
                new RankingItemDTO(0, usuarioLogado.getNome(), 1250, usuarioDTO.getAvatarUrl(), true),
                new RankingItemDTO(0, "Bruno", 980, "https://placehold.co/40x40/3b82f6/FFFFFF?text=B", false)
        ));

        // 2. Busca e adapta o ranking externo
        List<RankingItemDTO> rankingExterno = rankingAdapter.getAdaptedRanking();

        // 3. Combina os dois rankings
        List<RankingItemDTO> rankingCompleto = Stream.concat(rankingInterno.stream(), rankingExterno.stream())
                .sorted((r1, r2) -> Integer.compare(r2.getPontos(), r1.getPontos())) // Ordena por pontos
                .collect(Collectors.toList());

        // 4. Recalcula as posições
        for (int i = 0; i < rankingCompleto.size(); i++) {
            rankingCompleto.get(i).setPosicao(i + 1);
        }

        dashboardData.setRanking(rankingCompleto);
        // --- FIM DA LÓGICA DO ADAPTER ---

        model.addAttribute("dashboardData", dashboardData);
        return "dashboard";
    }
}

