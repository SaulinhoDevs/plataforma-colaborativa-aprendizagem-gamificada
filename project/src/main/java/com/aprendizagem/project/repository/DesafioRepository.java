package com.aprendizagem.project.repository;

import com.aprendizagem.project.desafios.Desafio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesafioRepository extends JpaRepository<Desafio, Long> {
}