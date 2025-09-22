package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.DashboardDataDTO;
import com.aprendizagem.project.dto.QuizResumoDTO;
import com.aprendizagem.project.dto.RankingItemDTO;
import com.aprendizagem.project.dto.UsuarioDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Arrays;

@Controller
public class DashboardController {

    // Futuramente, injetaremos um service para buscar os dados reais
    // @Autowired
    // private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model, Principal principal) {
        String userEmail = principal.getName();

        // **LÓGICA PROVISÓRIA COM DADOS MOCKADOS**
        DashboardDataDTO dashboardData = new DashboardDataDTO();

        // Criar um UsuarioDTO de exemplo - ESTA É A CORREÇÃO
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNome("Admin"); // Usando um nome de exemplo
        usuario.setAvatarUrl("https://placehold.co/40x40/9333ea/FFFFFF?text=A");

        dashboardData.setUsuario(usuario); // Adicionando o usuário ao DTO principal
        dashboardData.setPontosTotais(1250);
        dashboardData.setQuizzesConcluidos(15);
        dashboardData.setPrecisao(88);

        // Lista de quizzes em progresso
        dashboardData.setQuizzesEmProgresso(Arrays.asList(
                new QuizResumoDTO("Capitais da Europa", "Geografia", 60),
                new QuizResumoDTO("Revolução Francesa", "História", 30)
        ));

        // Lista de novos quizzes
        dashboardData.setNovosQuizzes(Arrays.asList(
                new QuizResumoDTO("Sistema Solar", "Astronomia", 0),
                new QuizResumoDTO("Biomas Brasileiros", "Biologia", 0)
        ));

        // Lista de conquistas
        dashboardData.setConquistas(Arrays.asList("🏅", "🌍", "🏆"));

        // Lista de ranking
        dashboardData.setRanking(Arrays.asList(
                new RankingItemDTO(1, "Ana", 1850, "https://placehold.co/40x40/22c55e/FFFFFF?text=A"),
                new RankingItemDTO(2, "Você", 1250, usuario.getAvatarUrl()),
                new RankingItemDTO(3, "Bruno", 980, "https://placehold.co/40x40/3b82f6/FFFFFF?text=B")
        ));

        model.addAttribute("dashboardData", dashboardData);
        return "dashboard";
    }
}

