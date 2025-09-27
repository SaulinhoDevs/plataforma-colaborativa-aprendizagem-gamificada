package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioQuizProgressoRepository extends JpaRepository<UsuarioQuizProgresso, Long> {
    Optional<UsuarioQuizProgresso> findByUsuarioAndQuiz(Usuario usuario, Quiz quiz);

    long countByUsuario(Usuario usuario);

    List<UsuarioQuizProgresso> findByQuizId(Long quizId);

    // ADICIONADO: Método para verificar se um utilizador completou um quiz num intervalo de tempo
    boolean existsByUsuarioAndConcluidoEmBetween(Usuario usuario, LocalDateTime inicio, LocalDateTime fim);

    // NOVOS MÉTODOS PARA DASHBOARD E ESTATÍSTICAS:

    /**
     * Calcula o total de pontos ganhos por um usuário
     */
    @Query("SELECT COALESCE(SUM(p.pontosGanhos), 0) FROM UsuarioQuizProgresso p WHERE p.usuario = :usuario")
    Integer calcularPontosTotais(@Param("usuario") Usuario usuario);

    /**
     * Calcula a precisão média de um usuário (percentual de acertos)
     */
    @Query("SELECT COALESCE(AVG(CAST(p.acertos AS double) / p.totalPerguntas * 100), 0) FROM UsuarioQuizProgresso p WHERE p.usuario = :usuario")
    Double calcularPrecisaoMedia(@Param("usuario") Usuario usuario);

    /**
     * Busca o progresso de um usuário em quizzes específicos
     */
    @Query("SELECT p FROM UsuarioQuizProgresso p WHERE p.usuario = :usuario AND p.quiz.id IN :quizIds")
    List<UsuarioQuizProgresso> findProgressoByUsuarioAndQuizIds(@Param("usuario") Usuario usuario, @Param("quizIds") List<Long> quizIds);

    /**
     * Busca todos os quizzes que um usuário já completou (para saber quais NÃO aparecem como "novos")
     */
    @Query("SELECT p.quiz.id FROM UsuarioQuizProgresso p WHERE p.usuario = :usuario")
    List<Long> findQuizIdsCompletadosByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Busca os últimos progressos de um usuário (para mostrar atividade recente)
     */
    @Query("SELECT p FROM UsuarioQuizProgresso p WHERE p.usuario = :usuario ORDER BY p.concluidoEm DESC")
    List<UsuarioQuizProgresso> findUltimosProgressosByUsuario(@Param("usuario") Usuario usuario);

    /**
     * Calcula a média de pontos de todos os usuários (para comparação no ranking)
     */
    @Query("SELECT COALESCE(AVG(subquery.totalPontos), 0) FROM (SELECT SUM(p.pontosGanhos) as totalPontos FROM UsuarioQuizProgresso p GROUP BY p.usuario) as subquery")
    Double calcularMediaPontosGeral();

    /**
     * Busca top usuários por pontos (para ranking)
     */
    @Query("SELECT p.usuario, SUM(p.pontosGanhos) as totalPontos FROM UsuarioQuizProgresso p " +
           "GROUP BY p.usuario ORDER BY totalPontos DESC")
    List<Object[]> findTopUsuariosPorPontos();
}