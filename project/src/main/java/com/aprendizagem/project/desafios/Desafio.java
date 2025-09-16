package com.aprendizagem.project.desafios;

import com.aprendizagem.project.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    // Professor que criou
    @ManyToOne
    private Usuario criador;

    private LocalDateTime availableFrom;
    private LocalDateTime availableUntil;

    private boolean fechado = false;
}