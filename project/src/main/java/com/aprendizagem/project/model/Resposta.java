package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "pergunta") // MUDANÇA CRÍTICA: Exclui o campo "pergunta" para quebrar o ciclo
@NoArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private boolean correta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    public Resposta(String texto, boolean correta) {
        this.texto = texto;
        this.correta = correta;
    }
}

