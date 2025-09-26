package com.aprendizagem.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespostaDTO {
    private Long id;
    private String text; // Corrigido de 'texto' para 'text'
    private boolean correct; // Corrigido de 'correta' para 'correct'
}

