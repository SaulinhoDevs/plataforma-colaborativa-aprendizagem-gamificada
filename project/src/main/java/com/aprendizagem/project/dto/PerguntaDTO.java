package com.aprendizagem.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerguntaDTO {
    private Long id;
    private String texto;
    private List<RespostaDTO> respostas;
}