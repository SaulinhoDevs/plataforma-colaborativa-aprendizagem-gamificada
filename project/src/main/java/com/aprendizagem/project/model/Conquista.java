package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe base abstrata para o padrão Composite.
 * Representa o componente "Component" que define a interface comum para
 * objetos simples (folhas) e compostos.
 *
 * Usaremos a estratégia de herança SINGLE_TABLE para armazenar todos os tipos
 * de conquistas (simples e grupos) na mesma tabela.
 */
@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_conquista", discriminatorType = DiscriminatorType.STRING)
public abstract class Conquista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    private String descricao;

    private String icone;

    public Conquista(String nome, String descricao, String icone) {
        this.nome = nome;
        this.descricao = descricao;
        this.icone = icone;
    }

    // Métodos para o padrão Composite (serão implementados nas subclasses)
    public void adicionar(Conquista componente) {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    public void remover(Conquista componente) {
        throw new UnsupportedOperationException("Operação não suportada.");
    }
}

