package com.aprendizagem.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para transportar os dados a serem exibidos na página de perfil do utilizador.
 */
@Data
@NoArgsConstructor
public class PerfilDTO {
    private String nome;
    private String email;
    private String avatarUrl;
    private int nivel;
    private int pontosTotais;
    private int quizzesConcluidos;
    private int precisaoMedia;
    private List<String> conquistasIcones; // Apenas os ícones para uma exibição simples
}

