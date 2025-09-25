package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // EntityGraph garante fetch de perguntas + respostas em uma só operação
    @EntityGraph(attributePaths = {"perguntas", "perguntas.respostas"})
    Optional<Quiz> findWithPerguntasRespostasById(Long id);
}