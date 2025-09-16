package com.aprendizagem.project.repository;

import com.aprendizagem.project.usuarios.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitanteRepository extends JpaRepository<Visitante, Long> {
}