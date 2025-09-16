package com.aprendizagem.project.controllers;

import com.aprendizagem.project.service.UsuarioService;
import com.aprendizagem.project.usuarios.Usuario;
import com.aprendizagem.project.dto.CreateUsuarioDTO;
import com.aprendizagem.project.usuarios.factory.AlunoFactory;
import com.aprendizagem.project.usuarios.factory.ProfessorFactory;
import com.aprendizagem.project.usuarios.factory.UsuarioFactory;
import com.aprendizagem.project.usuarios.factory.VisitanteFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/criar/{tipo}")
    public ResponseEntity<Usuario> criarUsuario(@PathVariable String tipo,
                                                @RequestBody CreateUsuarioDTO dto) {
        UsuarioFactory factory;
        switch (tipo.toLowerCase()) {
            case "aluno" -> factory = new AlunoFactory();
            case "professor" -> factory = new ProfessorFactory();
            case "visitante" -> factory = new VisitanteFactory();
            default -> throw new IllegalArgumentException("Tipo inválido: " + tipo);
        }

        Usuario usuario = factory.criarUsuario(dto.getNome(), dto.getEmail());
        Usuario salvo = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario u = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(u);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
}