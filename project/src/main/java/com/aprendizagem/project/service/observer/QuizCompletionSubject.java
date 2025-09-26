package com.aprendizagem.project.service.observer;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o "Subject" (Sujeito) no padrão Observer.
 * A sua responsabilidade é gerir uma lista de observadores e notificá-los
 * quando um evento relevante ocorre.
 */
@Component
public class QuizCompletionSubject {

    // Lista de todos os observadores que estão "a ouvir" os eventos
    private final List<QuizCompletionObserver> observers = new ArrayList<>();

    /**
     * Permite que novos observadores se registem para receber notificações.
     * @param observer O observador a ser adicionado.
     */
    public void addObserver(QuizCompletionObserver observer) {
        observers.add(observer);
    }

    /**
     * Permite que observadores se desregistem.
     * @param observer O observador a ser removido.
     */
    public void removeObserver(QuizCompletionObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifica todos os observadores registados sobre um novo evento.
     * @param event O evento que ocorreu.
     */
    public void notifyObservers(QuizCompletionEvent event) {
        // Percorre a lista e chama o método 'update' de cada observador
        for (QuizCompletionObserver observer : observers) {
            observer.update(event);
        }
    }
}
