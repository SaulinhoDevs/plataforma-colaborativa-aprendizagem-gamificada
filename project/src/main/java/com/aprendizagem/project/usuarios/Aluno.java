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
    // cache opcional para performance (atualizar quando salvar resultados)
    private Integer cachedTotalPoints = 0;
    private Integer nivel = 1;
}