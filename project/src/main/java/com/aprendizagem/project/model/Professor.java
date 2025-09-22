package com.aprendizagem.project.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("PROFESSOR")
public class Professor extends Usuario {
    private String disciplina;

    public Professor(String nome, String email, String password) {
        // Corrigido: Chama o construtor da superclasse com os parâmetros corretos.
        super(nome, email, password);
    }

    // Getters e setters são gerados pelo @Data do Lombok
}

