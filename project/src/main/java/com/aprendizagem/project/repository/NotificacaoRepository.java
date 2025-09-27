package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.Notificacao;
import com.aprendizagem.project.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findTop20ByUsuarioOrUsuarioIsNullOrderByCriadaEmDesc(Usuario usuario);
    long countByUsuarioAndLidaFalse(Usuario usuario);
}
