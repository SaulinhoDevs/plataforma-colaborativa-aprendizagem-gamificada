package com.aprendizagem.project.config;

import com.aprendizagem.project.enums.TipoUsuario;
import com.aprendizagem.project.model.*;
import com.aprendizagem.project.model.factory.UsuarioFactory;
import com.aprendizagem.project.repository.ConquistaRepository;
import com.aprendizagem.project.repository.QuizRepository;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConquistaRepository conquistaRepository; // ADICIONADO

    public DataInitializer(UsuarioRepository usuarioRepository, QuizRepository quizRepository,
                           PasswordEncoder passwordEncoder, ConquistaRepository conquistaRepository) { // ADICIONADO
        this.usuarioRepository = usuarioRepository;
        this.quizRepository = quizRepository;
        this.passwordEncoder = passwordEncoder;
        this.conquistaRepository = conquistaRepository; // ADICIONADO
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // ... (código existente para criar utilizador e quizzes)

        // ADICIONADO: Cria conquistas de exemplo se não existirem
        if (conquistaRepository.count() == 0) {
            criarConquistasIniciais();
            System.out.println(">>> Conquistas de exemplo criadas com sucesso!");
        }
    }

    // ... (métodos existentes criarQuizDeGeografia e criarQuizDeHistoria)

    /**
     * Cria um conjunto de conquistas iniciais na base de dados.
     */
    private void criarConquistasIniciais() {
        Conquista primeiraConquista = new Conquista(
                "Iniciante Curioso",
                "Completou o seu primeiro quiz!",
                "🏅"
        );
        conquistaRepository.save(primeiraConquista);

        Conquista mestreGeografia = new Conquista(
                "Mestre em Geografia",
                "Completou um quiz na categoria de Geografia.",
                "🌍"
        );
        conquistaRepository.save(mestreGeografia);

         Conquista historiador = new Conquista(
                "Historiador Nato",
                "Completou um quiz na categoria de História.",
                "📜"
        );
        conquistaRepository.save(historiador);
    }
    
    // Métodos existentes para criar quizzes (não precisam de ser alterados)
    private void criarQuizDeGeografia() {
        Quiz quizGeografia = new Quiz("Capitais da Europa", "Teste os seus conhecimentos sobre as capitais europeias.", "Geografia");
        Pergunta p1 = new Pergunta("Qual é a capital da França?");
        p1.setRespostas(new HashSet<>(Arrays.asList(
                new Resposta("Paris", true), new Resposta("Londres", false),
                new Resposta("Berlim", false), new Resposta("Madrid", false) )));
        Pergunta p2 = new Pergunta("Qual é a capital da Itália?");
        p2.setRespostas(new HashSet<>(Arrays.asList(
                new Resposta("Veneza", false), new Resposta("Roma", true),
                new Resposta("Milão", false), new Resposta("Florença", false) )));
        quizGeografia.setPerguntas(new HashSet<>(Arrays.asList(p1, p2)));
        quizRepository.save(quizGeografia);
    }

    private void criarQuizDeHistoria() {
        Quiz quizHistoria = new Quiz("Revolução Francesa", "Perguntas sobre um dos eventos mais marcantes da história.", "História");
        Pergunta p1 = new Pergunta("Em que ano começou a Revolução Francesa?");
        p1.setRespostas(new HashSet<>(Arrays.asList(
                new Resposta("1789", true), new Resposta("1815", false),
                new Resposta("1799", false), new Resposta("1776", false) )));
        quizHistoria.setPerguntas(Set.of(p1));
        quizRepository.save(quizHistoria);
    }
}

