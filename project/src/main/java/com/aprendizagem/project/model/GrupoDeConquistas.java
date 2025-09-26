package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa o "Composto" (Composite) no padrão Composite.
 * É um grupo que pode conter outras conquistas (simples ou outros grupos).
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("GRUPO")
public class GrupoDeConquistas extends Conquista {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "grupo_id") // Define a chave estrangeira na tabela Conquista
    private Set<Conquista> componentes = new HashSet<>();

    public GrupoDeConquistas(String nome, String descricao, String icone) {
        super(nome, descricao, icone);
    }

    /**
     * Adiciona um componente (outra conquista ou grupo) a este grupo.
     * @param componente A conquista a ser adicionada.
     */
    @Override
    public void adicionar(Conquista componente) {
        componentes.add(componente);
    }

    /**
     * Remove um componente deste grupo.
     * @param componente A conquista a ser removida.
     */
    @Override
    public void remover(Conquista componente) {
        componentes.remove(componente);
    }
}
