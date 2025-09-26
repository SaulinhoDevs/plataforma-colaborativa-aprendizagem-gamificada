package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Conquista;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioConquista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioConquistaRepository extends JpaRepository<UsuarioConquista, Long> {
    boolean existsByUsuarioAndConquista(Usuario usuario, Conquista conquista);
}
