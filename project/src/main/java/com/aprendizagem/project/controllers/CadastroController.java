package com.aprendizagem.project.controllers;

import com.aprendizagem.project.dto.CadastroRequest;
import com.aprendizagem.project.enums.TipoUsuario;
import com.aprendizagem.project.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CadastroController {

    private final UsuarioService usuarioService;

    public CadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cadastroRequest", new CadastroRequest());
        model.addAttribute("tiposUsuario", TipoUsuario.values());
        return "cadastro"; // Nome do template SEM extensão
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute CadastroRequest cadastroRequest) {
        try {
            usuarioService.cadastrarUsuario(cadastroRequest);
            return "redirect:/cadastro?success";
        } catch (Exception e) {
            return "redirect:/cadastro?error=" + e.getMessage();
        }
    }
}