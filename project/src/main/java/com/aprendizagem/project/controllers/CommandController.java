package com.aprendizagem.project.controllers;

import com.aprendizagem.project.service.command.CommandInvoker;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/command")
public class CommandController {

    private final CommandInvoker commandInvoker;

    public CommandController(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    /**
     * Endpoint para desfazer o último comando.
     * A anotação @PreAuthorize garante que apenas utilizadores com a role 'PROFESSOR'
     * possam aceder a este método.
     * @return Uma resposta HTTP.
     */
    @PostMapping("/undo")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> undo() {
        commandInvoker.undoLastCommand();
        return ResponseEntity.ok().build();
    }
}
