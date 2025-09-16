package com.aprendizagem.project.gamificacao.composite;

import java.util.ArrayList;
import java.util.List;

public class ConjuntoConquistas implements Conquista {

    private String nome;
    private List<Conquista> conquistas = new ArrayList<>();

    public ConjuntoConquistas(String nome) {
        this.nome = nome;
    }

    public void adicionar(Conquista conquista) {
        conquistas.add(conquista);
    }

    public void remover(Conquista conquista) {
        conquistas.remove(conquista);
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getPontos() {
        return conquistas.stream().mapToInt(Conquista::getPontos).sum();
    }

    public List<Conquista> getConquistas() {
        return conquistas;
    }
}