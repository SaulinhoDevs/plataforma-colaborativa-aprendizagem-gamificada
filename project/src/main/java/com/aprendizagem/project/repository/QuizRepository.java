package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // Aqui podemos adicionar métodos de busca customizados no futuro,
    // como por exemplo: findByCategoria(String categoria);
}
