package com.aprendizagem.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDataDTO {
    private Long id;
    private String titulo;
    private String categoria;
    private List<PerguntaDTO> perguntas;
}