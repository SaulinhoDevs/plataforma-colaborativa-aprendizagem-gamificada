package com.aprendizagem.project.service;

import com.aprendizagem.project.dto.DashboardDataDTO;
import com.aprendizagem.project.dto.QuizResumoDTO;
import com.aprendizagem.project.dto.RankingItemDTO;
import com.aprendizagem.project.dto.UsuarioDTO;
import com.aprendizagem.project.model.Professor;
import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import com.aprendizagem.project.repository.QuizRepository;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import com.aprendizagem.project.repository.UsuarioRepository;
import com.aprendizagem.project.service.integration.ExternalRankingAdapter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service responsável por montar os dados do dashboard.
 * Substitui os dados hardcoded por cálculos reais baseados no banco de dados.
 */
@Service
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final QuizRepository quizRepository;
    private final UsuarioQuizProgressoRepository progressoRepository;
    private final ExternalRankingAdapter rankingAdapter;

    public DashboardService(UsuarioRepository usuarioRepository,
                          QuizRepository quizRepository,
                          UsuarioQuizProgressoRepository progressoRepository,
                          ExternalRankingAdapter rankingAdapter) {
        this.usuarioRepository = usuarioRepository;
        this.quizRepository = quizRepository;
        this.progressoRepository = progressoRepository;
        this.rankingAdapter = rankingAdapter;
    }

    /**
     * Método principal que monta todos os dados do dashboard para um usuário.
     * @param usuario O usuário logado
     * @return DTO com todos os dados necessários para o dashboard
     */
    public DashboardDataDTO montarDashboardData(Usuario usuario) {
        DashboardDataDTO dashboardData = new DashboardDataDTO();

        // 1. Dados básicos do usuário
        dashboardData.setUsuario(criarUsuarioDTO(usuario));

        // 2. Identifica se é professor ou aluno
        dashboardData.setProfessor(usuario instanceof Professor);

        if (usuario instanceof Professor) {
            // Dashboard do professor (mais simples)
            montarDashboardProfessor(dashboardData, usuario);
        } else {
            // Dashboard do aluno (com estatísticas e gamificação)
            montarDashboardAluno(dashboardData, usuario);
        }

        return dashboardData;
    }

    /**
     * Monta dados específicos para o dashboard do professor.
     */
    private void montarDashboardProfessor(DashboardDataDTO dashboardData, Usuario professor) {
        // Professores têm dashboard mais simples, focado em gestão
        // Por enquanto, dados básicos. Pode ser expandido no futuro.
        dashboardData.setPontosTotais(0);
        dashboardData.setQuizzesConcluidos(0);
        dashboardData.setPrecisao(0);
        dashboardData.setNovosQuizzes(new ArrayList<>());
        dashboardData.setQuizzesEmProgresso(new ArrayList<>());
        dashboardData.setConquistas(new ArrayList<>());
        dashboardData.setRanking(new ArrayList<>());
    }

    /**
     * Monta dados completos para o dashboard do aluno com estatísticas reais.
     */
    private void montarDashboardAluno(DashboardDataDTO dashboardData, Usuario aluno) {
        // 1. Estatísticas reais do usuário
        Integer pontosTotais = progressoRepository.calcularPontosTotais(aluno);
        Long quizzesConcluidos = progressoRepository.countByUsuario(aluno);
        Double precisaoMedia = progressoRepository.calcularPrecisaoMedia(aluno);

        dashboardData.setPontosTotais(pontosTotais != null ? pontosTotais : 0);
        dashboardData.setQuizzesConcluidos(quizzesConcluidos != null ? quizzesConcluidos.intValue() : 0);
        dashboardData.setPrecisao(precisaoMedia != null ? precisaoMedia.intValue() : 0);

        // 2. Quizzes - separar entre novos e em progresso
        List<QuizResumoDTO> quizzesDisponiveis = montarQuizzesDisponiveis(aluno);
        dashboardData.setNovosQuizzes(filtrarNovosQuizzes(quizzesDisponiveis, aluno));
        dashboardData.setQuizzesEmProgresso(filtrarQuizzesEmProgresso(quizzesDisponiveis, aluno));

        // 3. Conquistas - por enquanto básicas, pode ser expandido
        dashboardData.setConquistas(montarConquistasBasicas(quizzesConcluidos.intValue()));

        // 4. Ranking combinado (interno + externo)
        dashboardData.setRanking(montarRankingCompleto(aluno));
    }

    /**
     * Cria DTO com dados básicos do usuário.
     */
    private UsuarioDTO criarUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setAvatarUrl("https://placehold.co/40x40/9333ea/FFFFFF?text=" + 
                               usuario.getNome().substring(0, 1).toUpperCase());
        usuarioDTO.setNivelDescricao("Nível 5 - Mestre Curioso"); // Pode ser calculado no futuro
        return usuarioDTO;
    }

    /**
     * Monta lista de quizzes disponíveis com progresso real.
     */
    private List<QuizResumoDTO> montarQuizzesDisponiveis(Usuario usuario) {
        List<Quiz> todosQuizzes = quizRepository.findAll();
        List<UsuarioQuizProgresso> progressosUsuario = progressoRepository.findUltimosProgressosByUsuario(usuario);

        return todosQuizzes.stream().map(quiz -> {
            // Verifica se o usuário tem progresso neste quiz
            UsuarioQuizProgresso progresso = progressosUsuario.stream()
                    .filter(p -> p.getQuiz().getId().equals(quiz.getId()))
                    .findFirst()
                    .orElse(null);

            if (progresso != null) {
                // Quiz concluído - usar construtor específico
                return new QuizResumoDTO(quiz.getId(), quiz.getTitulo(), quiz.getCategoria(), progresso.getPontosGanhos());
            } else {
                // Quiz novo - usar construtor para novo
                return new QuizResumoDTO(quiz.getId(), quiz.getTitulo(), quiz.getCategoria());
            }
        }).collect(Collectors.toList());
    }

    /**
     * Filtra quizzes que são novos para o usuário (nunca foram tentados).
     */
    private List<QuizResumoDTO> filtrarNovosQuizzes(List<QuizResumoDTO> quizzes, Usuario usuario) {
        return quizzes.stream()
                .filter(QuizResumoDTO::isNovo)
                .collect(Collectors.toList());
    }

    /**
     * Filtra quizzes que estão em progresso (iniciados mas não completados).
     * Por enquanto, como nosso sistema só salva quizzes completos, esta lista será vazia.
     * Pode ser expandido no futuro para suportar salvamento de progresso parcial.
     */
    private List<QuizResumoDTO> filtrarQuizzesEmProgresso(List<QuizResumoDTO> quizzes, Usuario usuario) {
        return quizzes.stream()
                .filter(QuizResumoDTO::isEmProgresso)
                .collect(Collectors.toList());
    }

    /**
     * Monta lista básica de conquistas baseada no número de quizzes concluídos.
     */
    private List<String> montarConquistasBasicas(int quizzesConcluidos) {
        List<String> conquistas = new ArrayList<>();
        
        if (quizzesConcluidos >= 1) {
            conquistas.add("🏅"); // Primeira conquista
        }
        if (quizzesConcluidos >= 5) {
            conquistas.add("🌍"); // Explorador
        }
        if (quizzesConcluidos >= 10) {
            conquistas.add("🏆"); // Mestre
        }
        if (quizzesConcluidos >= 20) {
            conquistas.add("👑"); // Lenda
        }

        return conquistas;
    }

    /**
     * Monta ranking combinado (interno + externo) com posições corretas.
     */
    private List<RankingItemDTO> montarRankingCompleto(Usuario usuarioAtual) {
        // 1. Busca ranking interno (usuários do sistema)
        List<RankingItemDTO> rankingInterno = montarRankingInterno(usuarioAtual);

        // 2. Busca ranking externo (simulado)
        List<RankingItemDTO> rankingExterno = rankingAdapter.getAdaptedRanking();

        // 3. Combina e ordena por pontos
        List<RankingItemDTO> rankingCompleto = Stream.concat(
                rankingInterno.stream(),
                rankingExterno.stream()
        ).sorted((r1, r2) -> Integer.compare(r2.getPontos(), r1.getPontos()))
         .collect(Collectors.toList());

        // 4. Redefine posições após ordenação
        for (int i = 0; i < rankingCompleto.size(); i++) {
            rankingCompleto.get(i).setPosicao(i + 1);
        }

        return rankingCompleto;
    }

    /**
     * Monta ranking interno com usuários reais do sistema.
     */
    private List<RankingItemDTO> montarRankingInterno(Usuario usuarioAtual) {
        List<Object[]> usuariosComPontos = usuarioRepository.findUsuariosComPontuacaoParaRanking();
        
        return usuariosComPontos.stream()
                .limit(10) // Limita a 10 usuários para performance
                .map(row -> {
                    Usuario usuario = (Usuario) row[0];
                    Number pontos = (Number) row[1];
                    
                    boolean isUsuarioAtual = usuario.getId().equals(usuarioAtual.getId());
                    String avatarUrl = "https://placehold.co/40x40/" + 
                                     (isUsuarioAtual ? "9333ea" : "22c55e") + 
                                     "/FFFFFF?text=" + usuario.getNome().substring(0, 1).toUpperCase();

                    return new RankingItemDTO(
                            0, // Posição será definida depois
                            usuario.getNome(),
                            pontos.intValue(),
                            avatarUrl,
                            isUsuarioAtual
                    );
                })
                .collect(Collectors.toList());
    }
}