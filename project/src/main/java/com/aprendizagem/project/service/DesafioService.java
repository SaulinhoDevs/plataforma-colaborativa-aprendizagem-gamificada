package com.aprendizagem.project.service;

import com.aprendizagem.project.gamificacao.model.Desafio;
import com.aprendizagem.project.gamificacao.observer.DesafioFechadoEvent;
import com.aprendizagem.project.repository.DesafioRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final ApplicationEventPublisher publisher;

    public DesafioService(DesafioRepository desafioRepository,
                          ApplicationEventPublisher publisher) {
        this.desafioRepository = desafioRepository;
        this.publisher = publisher;
    }

    public Desafio criarDesafio(Desafio desafio) {
        return desafioRepository.save(desafio);
    }

    public List<Desafio> listarDesafios() {
        return desafioRepository.findAll();
    }

    public Desafio buscarPorId(Long id) {
        return desafioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado com id: " + id));
    }

    public Desafio fecharDesafio(Long id) {
        Desafio desafio = buscarPorId(id);
        desafio.setFechado(true);
        desafioRepository.save(desafio);

        // dispara o evento para Observers
        publisher.publishEvent(new DesafioFechadoEvent(this, desafio));
        return desafio;
    }
}