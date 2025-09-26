package com.aprendizagem.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class PerguntaDTO {
    private Long id;
    private String text; // Corrigido de 'texto' para 'text'
    private List<RespostaDTO> answers; // Corrigido de 'respostas' para 'answers'
}

