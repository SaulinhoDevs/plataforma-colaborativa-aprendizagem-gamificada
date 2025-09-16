package com.aprendizagem.project.repository;

import com.aprendizagem.project.gamificacao.model.MedalhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedalhaRepository extends JpaRepository<MedalhaEntity, Long> {
}