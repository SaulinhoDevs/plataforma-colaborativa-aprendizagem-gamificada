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
@DiscriminatorValue("VISITANTE")
public class Visitante extends Usuario {
    private boolean convidadoEspecial = false;

    public Visitante(String nome, String email, String password) {
        // Corrigido: Chama o construtor da superclasse com os parâmetros corretos.
        super(nome, email, password);
    }

    // Getters e setters são gerados pelo @Data do Lombok
}
