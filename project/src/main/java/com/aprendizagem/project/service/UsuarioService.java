package com.aprendizagem.project.service;

import com.aprendizagem.project.usuarios.Usuario;
import com.aprendizagem.project.repository.UsuarioRepository;
import com.aprendizagem.project.gamificacao.model.MedalhaEntity;
import com.aprendizagem.project.repository.MedalhaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MedalhaRepository medalhaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          MedalhaRepository medalhaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medalhaRepository = medalhaRepository;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<MedalhaEntity> listarConquistas(Long usuarioId) {
        buscarPorId(usuarioId); // só para validar se existe
        return medalhaRepository.findByUsuarioId(usuarioId);
    }
}