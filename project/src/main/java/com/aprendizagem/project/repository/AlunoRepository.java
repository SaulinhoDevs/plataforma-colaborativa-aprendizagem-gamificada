package com.aprendizagem.project.repository;

import com.aprendizagem.project.usuarios.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}