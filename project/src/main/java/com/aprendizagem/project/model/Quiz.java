package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "perguntas") // Evita recursão no toString
@EqualsAndHashCode(exclude = "perguntas") // Evita recursão no equals/hashCode
@EntityListeners(AuditingEntityListener.class)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String categoria;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Pergunta> perguntas = new HashSet<>();

    public Quiz(String titulo, String descricao, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    /**
     * ATUALIZADO: Método auxiliar robusto para adicionar uma pergunta.
     * Isto garante que o relacionamento bidirecional seja sempre consistente.
     * @param pergunta A pergunta a ser adicionada.
     */
    public void addPergunta(Pergunta pergunta) {
        this.perguntas.add(pergunta);
        pergunta.setQuiz(this);
    }
}

