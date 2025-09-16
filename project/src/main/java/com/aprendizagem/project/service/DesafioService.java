package com.aprendizagem.project.service;

import com.aprendizagem.project.gamificacao.model.Desafio;
import com.aprendizagem.project.repository.DesafioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesafioService {

    private final DesafioRepository desafioRepository;

    public DesafioService(DesafioRepository desafioRepository) {
        this.desafioRepository = desafioRepository;
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
}