package com.aprendizagem.project.service;

import com.aprendizagem.project.dto.PerguntaDTO;
import com.aprendizagem.project.dto.QuizDataDTO;
import com.aprendizagem.project.dto.QuizResultadoDTO;
import com.aprendizagem.project.dto.RespostaDTO;
import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioQuizProgresso;
import com.aprendizagem.project.repository.QuizRepository;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import com.aprendizagem.project.service.observer.QuizCompletionEvent;
import com.aprendizagem.project.service.observer.QuizCompletionSubject;
import com.aprendizagem.project.service.strategy.PontuacaoStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UsuarioQuizProgressoRepository progressoRepository;
    private final PontuacaoStrategy pontuacaoStrategy;
    private final QuizCompletionSubject completionSubject;

    public QuizService(QuizRepository quizRepository,
                       UsuarioQuizProgressoRepository progressoRepository,
                       @Qualifier("streakBonus") PontuacaoStrategy pontuacaoStrategy,
                       QuizCompletionSubject completionSubject,
                       ConquistaService conquistaService) {
        this.quizRepository = quizRepository;
        this.progressoRepository = progressoRepository;
        this.pontuacaoStrategy = pontuacaoStrategy;
        this.completionSubject = completionSubject;
        this.completionSubject.addObserver(conquistaService);
    }

    @Transactional(readOnly = true)
    public QuizDataDTO findQuizById(Long id) {
        // CORREÇÃO: Usa método com EntityGraph para evitar Lazy Loading
        Quiz quiz = quizRepository.findWithPerguntasRespostasById(id)
                .orElseThrow(() -> new RuntimeException("Quiz não encontrado com o id: " + id));
        return mapToQuizDataDTO(quiz);
    }

    @Transactional
    public void processarResultadoQuiz(QuizResultadoDTO resultadoDTO, Usuario usuario) {
        Quiz quiz = quizRepository.findById(resultadoDTO.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz não encontrado para submissão."));

        UsuarioQuizProgresso progresso = progressoRepository
                .findByUsuarioAndQuiz(usuario, quiz)
                .orElse(new UsuarioQuizProgresso(usuario, quiz));

        progresso.setAcertos(resultadoDTO.getPontuacao());
        progresso.setTotalPerguntas(resultadoDTO.getTotalPerguntas());
        progresso.setConcluidoEm(LocalDateTime.now());
        int pontosGanhos = pontuacaoStrategy.calcularPontos(progresso);
        progresso.setPontosGanhos(pontosGanhos);

        progressoRepository.save(progresso);
        
        completionSubject.notifyObservers(new QuizCompletionEvent(usuario, progresso));
    }

    /**
     * ADICIONADO: Lógica para reverter a submissão de um quiz.
     * @param quizId O ID do quiz cujo progresso será revertido.
     * @param usuario O utilizador para o qual a reversão será aplicada.
     */
    @Transactional
    public void reverterResultadoQuiz(Long quizId, Usuario usuario) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz não encontrado para reversão."));

        // Encontra o progresso e apaga-o
        progressoRepository.findByUsuarioAndQuiz(usuario, quiz)
                .ifPresent(progresso -> progressoRepository.delete(progresso));

        System.out.println(">>> Progresso do quiz " + quizId + " para o utilizador " + usuario.getNome() + " foi revertido.");
    }

    /**
     * CORRIGIDO: Mapeamento agora alinha nomes do Model com DTO
     * Model usa: texto/correta, DTO usa: text/correct
     */
    private QuizDataDTO mapToQuizDataDTO(Quiz quiz) {
        QuizDataDTO dto = new QuizDataDTO();
        dto.setId(quiz.getId());
        dto.setTitulo(quiz.getTitulo());
        dto.setCategoria(quiz.getCategoria());
        dto.setPerguntas(quiz.getPerguntas().stream().map(pergunta -> {
            PerguntaDTO perguntaDTO = new PerguntaDTO();
            perguntaDTO.setId(pergunta.getId());
            // CORREÇÃO: Model.getTexto() -> DTO.setText()
            perguntaDTO.setText(pergunta.getTexto());
            perguntaDTO.setAnswers(pergunta.getRespostas().stream().map(resposta -> {
                RespostaDTO respostaDTO = new RespostaDTO();
                respostaDTO.setId(resposta.getId());
                // CORREÇÃO: Model.getTexto() -> DTO.setText()
                respostaDTO.setText(resposta.getTexto());
                // CORREÇÃO: Model.isCorreta() -> DTO.setCorrect()
                respostaDTO.setCorrect(resposta.isCorreta());
                return respostaDTO;
            }).collect(Collectors.toList()));
            return perguntaDTO;
        }).collect(Collectors.toList()));
        return dto;
    }
}