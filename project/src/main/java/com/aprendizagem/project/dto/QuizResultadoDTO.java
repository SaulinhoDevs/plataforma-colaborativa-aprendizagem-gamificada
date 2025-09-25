package com.aprendizagem.project.dto;

import lombok.Data;

@Data
public class QuizResultadoDTO {
    private Long quizId;
    private int pontuacao; // Quantas perguntas o utilizador acertou
    private int totalPerguntas;
}
