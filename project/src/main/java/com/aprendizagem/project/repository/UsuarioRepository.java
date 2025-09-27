package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo email (essencial para o login)
    Optional<Usuario> findByEmail(String email);

    // NOVOS MÉTODOS PARA DASHBOARD E RANKING:

    /**
     * Busca usuários com seus totais de pontos para ranking
     * Retorna uma lista ordenada pelos pontos (maior para menor)
     */
    @Query("SELECT u, COALESCE(SUM(p.pontosGanhos), 0) as totalPontos " +
           "FROM Usuario u " +
           "LEFT JOIN UsuarioQuizProgresso p ON u = p.usuario " +
           "GROUP BY u " +
           "ORDER BY totalPontos DESC")
    List<Object[]> findUsuariosComPontuacaoParaRanking();

    /**
     * Busca top N usuários para ranking limitado
     */
    @Query("SELECT u, COALESCE(SUM(p.pontosGanhos), 0) as totalPontos " +
           "FROM Usuario u " +
           "LEFT JOIN UsuarioQuizProgresso p ON u = p.usuario " +
           "GROUP BY u " +
           "ORDER BY totalPontos DESC")
    List<Object[]> findTopUsuariosParaRanking(@Param("limit") int limit);

    /**
     * Verifica se um usuário existe e retorna dados básicos para cache
     */
    @Query("SELECT u.id, u.nome, u.email, u.tipo FROM Usuario u WHERE u.email = :email")
    Optional<Object[]> findBasicDataByEmail(@Param("email") String email);

    /**
     * Busca usuários ativos (que completaram pelo menos um quiz)
     */
    @Query("SELECT DISTINCT u FROM Usuario u " +
           "JOIN UsuarioQuizProgresso p ON u = p.usuario")
    List<Usuario> findUsuariosAtivos();

    /**
     * Conta total de usuários por tipo (para estatísticas administrativas)
     */
    @Query("SELECT u.tipo, COUNT(u) FROM Usuario u GROUP BY u.tipo")
    List<Object[]> countUsuariosPorTipo();

    /**
     * Busca usuários criados recentemente (últimos N dias)
     */
    @Query("SELECT u FROM Usuario u WHERE u.createdAt >= CURRENT_TIMESTAMP - :dias DAY")
    List<Usuario> findUsuariosRecentesPorDias(@Param("dias") int dias);

    /**
     * Busca dados completos do usuário com estatísticas calculadas
     * Para uso no dashboard e perfil
     */
    @Query("SELECT u, " +
           "COALESCE(SUM(p.pontosGanhos), 0) as pontosTotais, " +
           "COUNT(p) as quizzesConcluidos, " +
           "COALESCE(AVG(CAST(p.acertos AS double) / p.totalPerguntas * 100), 0) as precisaoMedia " +
           "FROM Usuario u " +
           "LEFT JOIN UsuarioQuizProgresso p ON u = p.usuario " +
           "WHERE u.email = :email " +
           "GROUP BY u")
    Optional<Object[]> findUsuarioComEstatisticas(@Param("email") String email);

    /**
     * Busca posição do usuário no ranking geral
     */
    @Query("SELECT COUNT(u2) + 1 FROM " +
           "(SELECT u, COALESCE(SUM(p.pontosGanhos), 0) as totalPontos " +
           "FROM Usuario u LEFT JOIN UsuarioQuizProgresso p ON u = p.usuario " +
           "GROUP BY u) as ranking(u, totalPontos), " +
           "(SELECT COALESCE(SUM(p2.pontosGanhos), 0) as userPontos " +
           "FROM Usuario u2 LEFT JOIN UsuarioQuizProgresso p2 ON u2 = p2.usuario " +
           "WHERE u2.email = :email) as userRanking(userPontos) " +
           "WHERE ranking.totalPontos > userRanking.userPontos")
    Integer findPosicaoNoRanking(@Param("email") String email);
}