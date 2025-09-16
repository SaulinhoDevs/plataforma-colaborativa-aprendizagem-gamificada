package com.aprendizagem.project.gamificacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enunciado;
    private int peso; // peso proporcional

    @ManyToOne
    private Desafio desafio;
}