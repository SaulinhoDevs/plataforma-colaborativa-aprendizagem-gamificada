package com.aprendizagem.project.gamificacao.model;

import com.aprendizagem.project.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MedalhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // "MEDALHA" ou "TACA"
    private String nome; // Ouro, Prata, Bronze
    private int pontos;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}