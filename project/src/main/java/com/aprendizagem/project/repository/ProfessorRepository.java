package com.aprendizagem.project.repository;

import com.aprendizagem.project.usuarios.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}