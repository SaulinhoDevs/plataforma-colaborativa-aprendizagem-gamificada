package com.aprendizagem.project.service;

import com.aprendizagem.project.model.Notificacao;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService {

    private final NotificacaoRepository repo;

    public NotificacaoService(NotificacaoRepository repo) {
        this.repo = repo;
    }

    public List<Notificacao> listarParaUsuario(Usuario usuario) {
        return repo.findTop20ByUsuarioOrUsuarioIsNullOrderByCriadaEmDesc(usuario);
    }

    public long contarNaoLidas(Usuario usuario) {
        return repo.countByUsuarioAndLidaFalse(usuario);
    }

    public void marcarComoLida(Long id) {
        repo.findById(id).ifPresent(n -> {
            n.setLida(true);
            repo.save(n);
        });
    }
}
