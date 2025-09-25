package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UsuarioQuizProgresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private int pontuacao; // Quantas perguntas acertou

    @Column(nullable = false)
    private int totalPerguntas; // Total de perguntas no quiz no momento da submissão

    public UsuarioQuizProgresso(Usuario usuario, Quiz quiz, int pontuacao, int totalPerguntas) {
        this.usuario = usuario;
        this.quiz = quiz;
        this.pontuacao = pontuacao;
        this.totalPerguntas = totalPerguntas;
    }

    // Método de conveniência para calcular a percentagem de progresso
    public int getProgressoPercentual() {
        if (totalPerguntas == 0) {
            return 0;
        }
        return (int) Math.round(((double) pontuacao / totalPerguntas) * 100);
    }
}
