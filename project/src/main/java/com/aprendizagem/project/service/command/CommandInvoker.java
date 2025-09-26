package com.aprendizagem.project.service.command;

import org.springframework.stereotype.Component;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Representa o "Invoker" (Invocador) no padrão de projeto Command.
 * A sua responsabilidade é receber comandos, executá-los e, opcionalmente,
 * manter um histórico para permitir operações de desfazer (undo).
 */
@Component
public class CommandInvoker {

    // Usamos um Deque (pilha) para guardar o histórico dos comandos executados.
    private final Deque<Command> commandHistory = new ArrayDeque<>();

    /**
     * Recebe um comando, executa-o e adiciona-o ao histórico.
     * @param command O comando a ser executado.
     */
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command); // Adiciona o comando ao topo da pilha
    }

    /**
     * Desfaz o último comando executado.
     */
    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop(); // Remove o último comando da pilha
            lastCommand.undo();
        } else {
            System.out.println("Nenhum comando para desfazer.");
        }
    }
}
