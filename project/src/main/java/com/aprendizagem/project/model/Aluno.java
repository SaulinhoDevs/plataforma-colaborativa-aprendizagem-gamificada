package com.aprendizagem.project.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true) // Garante que os campos da superclasse sejam usados no equals/hashCode
@NoArgsConstructor
@DiscriminatorValue("ALUNO")
public class Aluno extends Usuario {
    private int nivel = 1;

    public Aluno(String nome, String email, String password) {
        // Corrigido: Chama o construtor da superclasse com os parâmetros corretos.
        super(nome, email, password);
    }

    // Getters e setters são gerados pelo @Data do Lombok
}

