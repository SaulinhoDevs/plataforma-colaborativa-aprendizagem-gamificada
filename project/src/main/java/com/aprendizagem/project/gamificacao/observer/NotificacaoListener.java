package com.aprendizagem.project.gamificacao.observer;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoListener {

    @EventListener
    public void aoFecharDesafio(DesafioFechadoEvent event) {
        var desafio = event.getDesafio();
        System.out.println("📢 Notificação: O desafio '"
                + desafio.getTitulo() + "' foi encerrado!");
        // Aqui poderia ser enviado e-mail, push notification etc.
    }
}