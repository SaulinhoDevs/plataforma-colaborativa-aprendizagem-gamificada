package com.aprendizagem.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingItemDTO {

    private int posicao;
    private String nome;
    private int pontos;
    private String avatarUrl;
    private boolean isUsuarioAtual; // Campo para identificar o usuário logado

    // A anotação @Data do Lombok irá gerar automaticamente:
    // - getNome() - que é o que o Thymeleaf precisa
    // - e todos os outros getters, setters, etc.
}

