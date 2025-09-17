package com.aprendizagem.project.model;

import com.aprendizagem.project.enums.TipoUsuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VISITANTE")
public class Visitante extends Usuario {
    private boolean convidadoEspecial = false;

    public Visitante() {
    }

    public Visitante(String nome, String email, String senha) {
        super(nome, email, senha, TipoUsuario.VISITANTE);
    }

    public boolean isConvidadoEspecial() {
        return convidadoEspecial;
    }

    public void setConvidadoEspecial(boolean convidadoEspecial) {
        this.convidadoEspecial = convidadoEspecial;
    }
}