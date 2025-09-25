package com.aprendizagem.project.service;

import com.aprendizagem.project.dto.PerguntaDTO;
import com.aprendizagem.project.dto.QuizDataDTO;
import com.aprendizagem.project.dto.RespostaDTO;
import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Transactional(readOnly = true)
    public QuizDataDTO findQuizById(Long id) {
        // chama o método com EntityGraph para trazer perguntas+respostas em uma única query
        Quiz quiz = quizRepository.findWithPerguntasRespostasById(id)
                .orElseThrow(() -> new RuntimeException("Quiz não encontrado com o id: " + id));

        // opcional: forçar inicialização dentro da transação — geralmente não necessário com EntityGraph
        quiz.getPerguntas().size();
        quiz.getPerguntas().forEach(p -> p.getRespostas().size());

        return mapToQuizDataDTO(quiz);
    }

    private QuizDataDTO mapToQuizDataDTO(Quiz quiz) {
        QuizDataDTO dto = new QuizDataDTO();
        dto.setId(quiz.getId());
        dto.setTitulo(quiz.getTitulo());
        dto.setCategoria(quiz.getCategoria());

        // CORREÇÃO: A usar os nomes de DTO e setters em português para corresponder ao JS
        dto.setPerguntas(quiz.getPerguntas().stream().map(pergunta -> {
            PerguntaDTO perguntaDTO = new PerguntaDTO();
            perguntaDTO.setId(pergunta.getId());
            perguntaDTO.setTexto(pergunta.getTexto()); // Corrigido para setTexto

            perguntaDTO.setRespostas(pergunta.getRespostas().stream().map(resposta -> { // Corrigido para setRespostas
                RespostaDTO respostaDTO = new RespostaDTO();
                respostaDTO.setId(resposta.getId());
                respostaDTO.setTexto(resposta.getTexto()); // Corrigido para setTexto
                respostaDTO.setCorreta(resposta.isCorreta()); // Corrigido para setCorreta
                return respostaDTO;
            }).collect(Collectors.toList()));

            return perguntaDTO;
        }).collect(Collectors.toList()));

        return dto;
    }
}

