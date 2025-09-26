package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.data.jpa.repository.JpaRepository;
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
}

