package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioQuizProgressoRepository extends JpaRepository<UsuarioQuizProgresso, Long> {
    Optional<UsuarioQuizProgresso> findByUsuarioAndQuiz(Usuario usuario, Quiz quiz);

    long countByUsuario(Usuario usuario);

    // ADICIONADO: Método para buscar todos os progressos de um quiz específico.
    // Este método é utilizado pela nossa RelatorioFacade.
    List<UsuarioQuizProgresso> findByQuizId(Long quizId);
}

