package com.aprendizagem.project.usuarios.repository;

import com.aprendizagem.project.usuarios.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}