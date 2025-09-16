package com.aprendizagem.project.repository;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipacaoDesafioRepository extends JpaRepository<ParticipacaoDesafio, Long> {

    @Query("""
                SELECT p FROM ParticipacaoDesafio p
                WHERE p.desafio.id = :desafioId
                ORDER BY p.pontos DESC,
                         p.tempoTotalMs ASC,
                         p.maiorSequenciaAcertos DESC,
                         p.acertosPesados DESC,
                         p.finishedAt ASC
            """)
    List<ParticipacaoDesafio> findRankingByDesafio(Long desafioId);
}