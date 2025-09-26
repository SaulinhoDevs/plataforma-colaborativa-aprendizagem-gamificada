package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "pergunta") // Evita recursão no toString
@EqualsAndHashCode(exclude = "pergunta") // Evita recursão e foca no ID para a identidade
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private boolean correta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    public Resposta(String texto, boolean correta) {
        this.texto = texto;
        this.correta = correta;
    }
}

