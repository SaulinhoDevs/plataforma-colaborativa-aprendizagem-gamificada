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

    // A anotação @Data do Lombok irá gerar automaticamente:
    // - getPosicao(), getNome(), getPontos(), getAvatarUrl()
    // - setPosicao(...), setNome(...), etc.
    // - E outros métodos como toString(), equals(), hashCode()
}

