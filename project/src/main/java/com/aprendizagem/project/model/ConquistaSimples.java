package com.aprendizagem.project.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa a "Folha" (Leaf) no padrão Composite.
 * É uma conquista individual que não pode conter outras.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("SIMPLES")
public class ConquistaSimples extends Conquista {

    public ConquistaSimples(String nome, String descricao, String icone) {
        super(nome, descricao, icone);
    }

    // Como é uma folha, os métodos adicionar e remover não são suportados
    // e mantêm a implementação da superclasse que lança uma exceção.
}
