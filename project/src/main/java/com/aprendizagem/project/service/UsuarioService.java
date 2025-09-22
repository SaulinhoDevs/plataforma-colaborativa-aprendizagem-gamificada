package com.aprendizagem.project.service;

import com.aprendizagem.project.dto.CadastroRequest;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.factory.UsuarioFactory;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(CadastroRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail informado já está em uso.");
        }

        String senhaCriptografada = passwordEncoder.encode(request.getPassword());

        // Corrigido: a fábrica agora recebe o request e a senha criptografada separadamente.
        Usuario usuario = UsuarioFactory.createUsuario(request, senhaCriptografada);

        return usuarioRepository.save(usuario);
    }
}
