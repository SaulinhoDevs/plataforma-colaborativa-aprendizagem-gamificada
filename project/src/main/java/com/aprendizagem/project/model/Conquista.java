package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Conquista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome; // Ex: "Primeiro Quiz Concluído"

    private String descricao; // Ex: "Você completou o seu primeiro desafio!"

    private String icone; // Ex: "🏅" ou o nome de um ícone (ex: "bi-trophy")

    // Poderíamos adicionar critérios aqui no futuro (ex: tipo de critério, valor necessário)

    public Conquista(String nome, String descricao, String icone) {
        this.nome = nome;
        this.descricao = descricao;
        this.icone = icone;
    }
}
