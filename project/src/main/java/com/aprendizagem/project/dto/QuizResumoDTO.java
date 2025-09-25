package com.aprendizagem.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // Garante que o construtor usado no controller exista
public class QuizResumoDTO {
    // CORREÇÃO: Adicionado o campo 'id' que estava a faltar
    private Long id;
    private String titulo;
    private String categoria;
    private int progresso; // 0 para novos quizzes, > 0 para quizzes em progresso
}

