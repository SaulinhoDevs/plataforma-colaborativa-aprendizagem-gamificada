package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.DashboardDataDTO;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.UsuarioRepository;
import com.aprendizagem.project.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Controller responsável pelo dashboard principal da aplicação.
 * ATUALIZADO: Agora usa DashboardService para dados reais ao invés de hardcode.
 */
@Controller
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final DashboardService dashboardService; // NOVO: Service para lógica de dashboard

    public DashboardController(UsuarioRepository usuarioRepository,
                               DashboardService dashboardService) {
        this.usuarioRepository = usuarioRepository;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, Principal principal) {
        // 1. Busca o usuário logado
        String userEmail = principal.getName();
        Usuario usuarioLogado = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("Utilizador não encontrado no banco de dados."));

        // 2. MUDANÇA PRINCIPAL: Usa DashboardService para montar dados reais
        DashboardDataDTO dashboardData = dashboardService.montarDashboardData(usuarioLogado);

        // 3. Passa os dados para a view
        model.addAttribute("dashboardData", dashboardData);
        return "dashboard";
    }
}