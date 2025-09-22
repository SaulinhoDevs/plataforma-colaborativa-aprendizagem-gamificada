package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo email (essencial para o login)
    Optional<Usuario> findByEmail(String email);
}
