package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Conquista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConquistaRepository extends JpaRepository<Conquista, Long> {
    Optional<Conquista> findByNome(String nome);
}
