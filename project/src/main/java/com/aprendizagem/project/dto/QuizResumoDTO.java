package com.aprendizagem.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResumoDTO {
    private String titulo;
    private String categoria;
    private int progresso;
}

