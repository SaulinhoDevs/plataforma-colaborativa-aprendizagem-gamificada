package com.aprendizagem.project.usuarios;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("VISITANTE")
public class Visitante extends Usuario {
    private boolean convidadoEspecial;
}