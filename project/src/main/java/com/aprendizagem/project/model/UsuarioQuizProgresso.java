package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    // ADICIONADO: Campos para guardar o progresso e a pontuação
    private Integer acertos;
    private Integer totalPerguntas;
    private Integer pontosGanhos;

    @CreatedDate // Usaremos a data de criação como a data de conclusão
    @Column(nullable = false, updatable = false)
    private LocalDateTime concluidoEm;

    public UsuarioQuizProgresso(Usuario usuario, Quiz quiz) {
        this.usuario = usuario;
        this.quiz = quiz;
    }
}

