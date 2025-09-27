package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.PerfilDTO;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.UsuarioRepository;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsável pela página de perfil do usuário.
 * ATUALIZADO: Agora usa dados reais do banco ao invés de hardcode.
 */
@Controller
public class PerfilController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioQuizProgressoRepository progressoRepository; // ADICIONADO

    public PerfilController(UsuarioRepository usuarioRepository,
                            UsuarioQuizProgressoRepository progressoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.progressoRepository = progressoRepository;
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));

        // MUDANÇA PRINCIPAL: Busca dados reais do banco
        PerfilDTO perfilDTO = montarPerfilComDadosReais(usuario);

        model.addAttribute("perfilData", perfilDTO);
        return "perfil";
    }

    /**
     * NOVO MÉTODO: Monta perfil com dados reais calculados do banco de dados.
     */
    private PerfilDTO montarPerfilComDadosReais(Usuario usuario) {
        PerfilDTO perfilDTO = new PerfilDTO();

        // 1. Dados básicos do usuário
        perfilDTO.setNome(usuario.getNome());
        perfilDTO.setEmail(usuario.getEmail());
        perfilDTO.setAvatarUrl("https://placehold.co/120x120/9333ea/FFFFFF?text=" +
                usuario.getNome().substring(0, 1).toUpperCase());

        // 2. DADOS REAIS calculados do banco de dados
        Integer pontosTotais = progressoRepository.calcularPontosTotais(usuario);
        Long quizzesConcluidos = progressoRepository.countByUsuario(usuario);
        Double precisaoMedia = progressoRepository.calcularPrecisaoMedia(usuario);

        perfilDTO.setPontosTotais(pontosTotais != null ? pontosTotais : 0);
        perfilDTO.setQuizzesConcluidos(quizzesConcluidos != null ? quizzesConcluidos.intValue() : 0);
        perfilDTO.setPrecisaoMedia(precisaoMedia != null ? precisaoMedia.intValue() : 0);

        // 3. Nível calculado baseado nos pontos (novo algoritmo)
        perfilDTO.setNivel(calcularNivelPorPontos(perfilDTO.getPontosTotais()));

        // 4. Conquistas dinâmicas baseadas no progresso real
        perfilDTO.setConquistasIcones(calcularConquistasReais(perfilDTO.getQuizzesConcluidos(),
                perfilDTO.getPrecisaoMedia(),
                perfilDTO.getPontosTotais()));

        return perfilDTO;
    }

    /**
     * Calcula o nível do usuário baseado nos pontos totais.
     * Algoritmo: A cada 200 pontos = 1 nível (começa no nível 1).
     */
    private int calcularNivelPorPontos(int pontosTotais) {
        if (pontosTotais == 0) return 1;
        return Math.min((pontosTotais / 200) + 1, 50); // Máximo nível 50
    }

    /**
     * Calcula conquistas reais baseadas no desempenho do usuário.
     */
    private List<String> calcularConquistasReais(int quizzesConcluidos, int precisaoMedia, int pontosTotais) {
        List<String> conquistas = new ArrayList<>();

        // Conquistas por número de quizzes
        if (quizzesConcluidos >= 1) {
            conquistas.add("🏅"); // Primeira tentativa
        }
        if (quizzesConcluidos >= 5) {
            conquistas.add("🌍"); // Explorador
        }
        if (quizzesConcluidos >= 10) {
            conquistas.add("🏆"); // Dedicado
        }
        if (quizzesConcluidos >= 20) {
            conquistas.add("👑"); // Mestre
        }
        if (quizzesConcluidos >= 50) {
            conquistas.add("💎"); // Lenda
        }

        // Conquistas por precisão
        if (precisaoMedia >= 80) {
            conquistas.add("🎯"); // Atirador certeiro
        }
        if (precisaoMedia >= 95) {
            conquistas.add("🧠"); // Gênio
        }

        // Conquistas por pontos
        if (pontosTotais >= 500) {
            conquistas.add("⭐"); // Colecionador
        }
        if (pontosTotais >= 1000) {
            conquistas.add("🚀"); // Superstar
        }
        if (pontosTotais >= 2000) {
            conquistas.add("💫"); // Fenômeno
        }

        // Conquistas especiais (combinações)
        if (quizzesConcluidos >= 10 && precisaoMedia >= 90) {
            conquistas.add("🌟"); // Perfeccionista
        }
        if (pontosTotais >= 1500 && quizzesConcluidos >= 15) {
            conquistas.add("🔥"); // Imparável
        }

        return conquistas;
    }
}