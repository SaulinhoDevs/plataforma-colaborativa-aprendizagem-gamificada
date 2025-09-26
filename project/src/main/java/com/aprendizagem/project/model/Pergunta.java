package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"quiz", "respostas"}) // Evita recursão no toString
@EqualsAndHashCode(exclude = {"quiz", "respostas"}) // Evita recursão e foca no ID para a identidade
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Resposta> respostas = new HashSet<>();

    public Pergunta(String texto) {
        this.texto = texto;
    }

    /**
     * ATUALIZADO: Método auxiliar robusto para adicionar uma resposta.
     * Isto garante que o relacionamento bidirecional seja sempre consistente.
     * @param resposta A resposta a ser adicionada.
     */
    public void addResposta(Resposta resposta) {
        this.respostas.add(resposta);
        resposta.setPergunta(this);
    }
}

