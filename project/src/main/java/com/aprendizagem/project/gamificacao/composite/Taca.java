package com.aprendizagem.project.gamificacao.composite;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Taca implements Conquista {

    private String nome;
    private int pontos; // Ouro=50, Prata=40, Bronze=30

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getPontos() {
        return pontos;
    }
}