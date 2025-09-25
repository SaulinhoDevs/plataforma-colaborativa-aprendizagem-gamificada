package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "quiz") // MUDANÇA CRÍTICA: Exclui o campo "quiz" para quebrar o ciclo
@NoArgsConstructor
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    // antes: List<Resposta> respostas;
    // Use Set + SUBSELECT para evitar selects por cada pergunta após JOIN FETCH
    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Resposta> respostas = new HashSet<>();

    public Pergunta(String texto) {
        this.texto = texto;
    }

    public void setRespostas(Set<Resposta> respostas) {
        this.respostas.clear();
        if (respostas != null) {
            this.respostas.addAll(respostas);
            respostas.forEach(resposta -> resposta.setPergunta(this));
        }
    }
}

