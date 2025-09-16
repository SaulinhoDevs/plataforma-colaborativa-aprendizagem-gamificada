package com.aprendizagem.project.usuarios.repository;

import com.aprendizagem.project.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}