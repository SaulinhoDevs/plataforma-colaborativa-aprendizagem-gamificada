package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Lob
    private String mensagem;

    private boolean lida = false;

    private LocalDateTime criadaEm = LocalDateTime.now();

    @ManyToOne
    private Usuario usuario; // notification owner (can be null for global notifications)

    public Notificacao(String titulo, String mensagem, Usuario usuario) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.usuario = usuario;
        this.criadaEm = LocalDateTime.now();
    }
}
