package com.aprendizagem.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DashboardDataDTO {

    private UsuarioDTO usuario;
    private int pontosTotais;
    private int quizzesConcluidos;
    private int precisao;
    private List<QuizResumoDTO> quizzesEmProgresso;
    private List<QuizResumoDTO> novosQuizzes;
    private List<String> conquistas;
    private List<RankingItemDTO> ranking;

}

