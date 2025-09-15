package com.aprendizagem.project.usuarios;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ALUNO")
public class Aluno extends Usuario {
    private int pontos = 0;
    private int nivel = 1;
}