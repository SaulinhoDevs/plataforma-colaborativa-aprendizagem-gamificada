package com.aprendizagem.project.gamificacao.model;

import com.aprendizagem.project.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class ParticipacaoDesafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario aluno; // só ALUNO participa

    @ManyToOne
    private Desafio desafio;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RespostaQuestao> respostas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<com.aprendizagem.project.gamificacao.model.MedalhaEntity> medalhas;

    // resultados calculados
    private int pontos;
    private long tempoTotalMs;
    private int maiorSequenciaAcertos;
    private int acertosPesados;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}