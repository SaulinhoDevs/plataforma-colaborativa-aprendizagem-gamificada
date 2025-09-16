package com.aprendizagem.project.gamificacao.observer;

import com.aprendizagem.project.gamificacao.model.Desafio;
import org.springframework.context.ApplicationEvent;

public class DesafioFechadoEvent extends ApplicationEvent {
    private final Desafio desafio;

    public DesafioFechadoEvent(Object source, Desafio desafio) {
        super(source);
        this.desafio = desafio;
    }

    public Desafio getDesafio() {
        return desafio;
    }
}