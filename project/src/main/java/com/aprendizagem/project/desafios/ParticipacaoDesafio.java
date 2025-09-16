package com.aprendizagem.project.desafios;

import com.aprendizagem.project.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ParticipacaoDesafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario aluno; // apenas ALUNO deve participar

    @ManyToOne
    private Desafio desafio;

    private int pontos;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}