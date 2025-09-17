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

        String senhaCriptografada = passwordEncoder.encode(request.getPassword());

        Usuario usuario = UsuarioFactory.criarUsuario(
                request.getTipo(),
                request.getNome(),
                request.getEmail(),
                senhaCriptografada
        );

        return usuarioRepository.save(usuario);
    }
}