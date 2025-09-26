package com.aprendizagem.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * Mapeia a rota raiz ("/") para a página de boas-vindas.
     * @return O nome do template "index".
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
