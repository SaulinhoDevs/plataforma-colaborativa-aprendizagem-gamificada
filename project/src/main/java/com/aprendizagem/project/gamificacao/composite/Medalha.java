package com.aprendizagem.project.gamificacao.composite;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Medalha implements Conquista {

    private String nome;
    private int pontos; // Ouro=30, Prata=20, Bronze=10

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getPontos() {
        return pontos;
    }
}