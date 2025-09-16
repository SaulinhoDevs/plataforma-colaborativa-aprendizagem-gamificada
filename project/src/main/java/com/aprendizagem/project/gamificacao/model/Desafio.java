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
public class Desafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 2000)
    private String descricao;

    @ManyToOne
    private Usuario criador; // professor

    private LocalDateTime availableFrom;
    private LocalDateTime availableUntil;

    private int notaMaxima = 100;
    private boolean fechado = false;

    @OneToMany(mappedBy = "desafio", cascade = CascadeType.ALL)
    private List<Questao> questoes;
}