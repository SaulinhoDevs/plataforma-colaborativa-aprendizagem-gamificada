package com.aprendizagem.project.repository;

import com.aprendizagem.project.desafios.ParticipacaoDesafio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipacaoDesafioRepository extends JpaRepository<ParticipacaoDesafio, Long> {

    @Query("""
                SELECT p FROM ParticipacaoDesafio p
                WHERE p.desafio.id = :desafioId
                ORDER BY p.pontos DESC
            """)
    List<ParticipacaoDesafio> findRankingByDesafio(Long desafioId);
}