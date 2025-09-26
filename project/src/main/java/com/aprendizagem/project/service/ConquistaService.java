package com.aprendizagem.project.service;

import com.aprendizagem.project.model.Conquista;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.UsuarioConquista;
import com.aprendizagem.project.repository.ConquistaRepository;
import com.aprendizagem.project.repository.UsuarioConquistaRepository;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import com.aprendizagem.project.service.observer.QuizCompletionEvent;
import com.aprendizagem.project.service.observer.QuizCompletionObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConquistaService implements QuizCompletionObserver {

    private static final Logger logger = LoggerFactory.getLogger(ConquistaService.class);

    private final ConquistaRepository conquistaRepository;
    private final UsuarioConquistaRepository usuarioConquistaRepository;
    private final UsuarioQuizProgressoRepository progressoRepository;

    public ConquistaService(ConquistaRepository conquistaRepository,
                            UsuarioConquistaRepository usuarioConquistaRepository,
                            UsuarioQuizProgressoRepository progressoRepository) {
        this.conquistaRepository = conquistaRepository;
        this.usuarioConquistaRepository = usuarioConquistaRepository;
        this.progressoRepository = progressoRepository;
    }

    @Override
    @Transactional
    public void update(QuizCompletionEvent event) {
        logger.info("ConquistaService notificado: Utilizador {} completou o quiz {}",
                event.getUsuario().getId(), event.getProgresso().getQuiz().getId());

        verificarConquistas(event.getUsuario());
    }

    /**
     * Verifica todas as conquistas relevantes para um utilizador após ele completar um quiz.
     * @param usuario O utilizador a ser verificado.
     */
    private void verificarConquistas(Usuario usuario) {
        verificarConquistaPrimeiroQuiz(usuario);
        // Adicione chamadas para outras verificações de conquista aqui
        // Ex: verificarConquistaPorCategoria(usuario, "Geografia");
    }

    /**
     * Verifica se o utilizador deve ganhar a conquista "Iniciante Curioso".
     * @param usuario O utilizador.
     */
    private void verificarConquistaPrimeiroQuiz(Usuario usuario) {
        long quizzesConcluidos = progressoRepository.countByUsuario(usuario);

        if (quizzesConcluidos == 1) { // Se este foi o primeiro quiz que ele completou
            conquistaRepository.findByNome("Iniciante Curioso").ifPresent(conquista -> {
                atribuirConquista(usuario, conquista);
            });
        }
    }

    /**
     * Atribui uma conquista a um utilizador se ele ainda não a tiver.
     * @param usuario O utilizador.
     * @param conquista A conquista a ser atribuída.
     */
    private void atribuirConquista(Usuario usuario, Conquista conquista) {
        // Verifica se o utilizador já tem esta conquista para evitar duplicados
        boolean jaPossui = usuarioConquistaRepository.existsByUsuarioAndConquista(usuario, conquista);
        if (!jaPossui) {
            UsuarioConquista novaConquista = new UsuarioConquista(usuario, conquista);
            usuarioConquistaRepository.save(novaConquista);
            logger.info(">>> Conquista '{}' atribuída ao utilizador {}!", conquista.getNome(), usuario.getNome());
            // No futuro, poderíamos notificar o utilizador no frontend aqui
        }
    }
}

