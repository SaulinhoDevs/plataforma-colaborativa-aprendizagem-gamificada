package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioQuizProgressoRepository extends JpaRepository<UsuarioQuizProgresso, Long> {

    // Encontra todos os progressos de um determinado utilizador
    List<UsuarioQuizProgresso> findByUsuario(Usuario usuario);

}
