package com.aprendizagem.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MuralController {

    /**
     * Mapeia a rota /mural para exibir a página do mural coletivo.
     * @return O nome do template "mural".
     */
    @GetMapping("/mural")
    public String mostrarPaginaMural() {
        // Por agora, apenas exibimos a página. A lógica para carregar as publicações
        // será adicionada depois.
        return "mural";
    }
}
