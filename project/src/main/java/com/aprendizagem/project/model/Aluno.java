package com.aprendizagem.project.model;

import com.aprendizagem.project.enums.TipoUsuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ALUNO")
public class Aluno extends Usuario {
    private int nivel = 1;

    public Aluno() {
    }

    public Aluno(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.ALUNO);
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}