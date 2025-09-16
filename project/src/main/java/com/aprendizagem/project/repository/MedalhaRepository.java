package com.aprendizagem.project.repository;

import com.aprendizagem.project.gamificacao.model.MedalhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedalhaRepository extends JpaRepository<MedalhaEntity, Long> {
    List<MedalhaEntity> findByUsuarioId(Long usuarioId);
}