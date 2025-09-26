package com.aprendizagem.project.service.command;

/**
 * Interface para o padrão de projeto Command.
 * Define um contrato para todos os comandos concretos que encapsulam
 * uma ação ou um pedido como um objeto.
 */
public interface Command {

    /**
     * Executa a ação encapsulada pelo comando.
     */
    void execute();

    /**
     * Desfaz a ação executada.
     * (Será implementado no futuro para cumprir os requisitos do projeto).
     */
    void undo();
}
