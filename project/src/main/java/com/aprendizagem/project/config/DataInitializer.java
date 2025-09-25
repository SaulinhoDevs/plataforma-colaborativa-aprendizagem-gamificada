package com.aprendizagem.project.config;

import com.aprendizagem.project.enums.TipoUsuario;
import com.aprendizagem.project.model.Pergunta;
import com.aprendizagem.project.model.Quiz;
import com.aprendizagem.project.model.Resposta;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.factory.UsuarioFactory;
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

    public DataInitializer(UsuarioRepository usuarioRepository, QuizRepository quizRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.quizRepository = quizRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = UsuarioFactory.createUsuario(
                    "Admin",
                    "admin@admin.com",
                    passwordEncoder.encode("admin123"),
                    TipoUsuario.PROFESSOR
            );
            usuarioRepository.save(admin);
            System.out.println(">>> Utilizador admin de teste criado com sucesso!");
        }

        if (quizRepository.count() == 0) {
            criarQuizDeGeografia();
            criarQuizDeHistoria();
            System.out.println(">>> Quizzes de exemplo criados com sucesso!");
        }
    }

    private void criarQuizDeGeografia() {
        Quiz quizGeografia = new Quiz("Capitais da Europa", "Teste os seus conhecimentos sobre as capitais europeias.", "Geografia");

        Pergunta p1 = new Pergunta("Qual é a capital da França?");
        // MUDANÇA: Criar um HashSet a partir da lista
        p1.setRespostas(new HashSet<>(Arrays.asList(
                new Resposta("Paris", true),
                new Resposta("Londres", false),
                new Resposta("Berlim", false),
                new Resposta("Madrid", false)
        )));

        Pergunta p2 = new Pergunta("Qual é a capital da Itália?");
        p2.setRespostas(new HashSet<>(Arrays.asList(
                new Resposta("Veneza", false),
                new Resposta("Roma", true),
                new Resposta("Milão", false),
                new Resposta("Florença", false)
        )));

        quizGeografia.setPerguntas(new HashSet<>(Arrays.asList(p1, p2)));
        quizRepository.save(quizGeografia);
    }

    private void criarQuizDeHistoria() {
        Quiz quizHistoria = new Quiz("Revolução Francesa", "Perguntas sobre um dos eventos mais marcantes da história.", "História");

        Pergunta p1 = new Pergunta("Em que ano começou a Revolução Francesa?");
        p1.setRespostas(new HashSet<>(Arrays.asList(
                new Resposta("1789", true),
                new Resposta("1815", false),
                new Resposta("1799", false),
                new Resposta("1776", false)
        )));
        
        quizHistoria.setPerguntas(Set.of(p1));
        quizRepository.save(quizHistoria);
    }
}

