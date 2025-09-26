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
    private final ConquistaRepository conquistaRepository;

    public DataInitializer(UsuarioRepository usuarioRepository, QuizRepository quizRepository,
                           PasswordEncoder passwordEncoder, ConquistaRepository conquistaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.quizRepository = quizRepository;
        this.passwordEncoder = passwordEncoder;
        this.conquistaRepository = conquistaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = UsuarioFactory.createUsuario(
                    "Admin", "admin@admin.com",
                    passwordEncoder.encode("admin123"), TipoUsuario.PROFESSOR);
            usuarioRepository.save(admin);
            System.out.println(">>> Utilizador admin de teste criado com sucesso!");
        }

        if (quizRepository.count() == 0) {
            criarQuizzesDeExemplo();
            System.out.println(">>> Quizzes de exemplo criados com sucesso!");
        }

        // ADICIONADO: Lógica para criar a hierarquia de conquistas
        if (conquistaRepository.count() == 0) {
            criarEstruturaDeConquistas();
            System.out.println(">>> Estrutura de conquistas (Composite) criada com sucesso!");
        }
    }

    private void criarEstruturaDeConquistas() {
        // 1. Cria as conquistas simples (as "folhas")
        ConquistaSimples historiador = new ConquistaSimples(
                "Historiador Nato",
                "Completou um quiz na categoria de História.",
                "📜"
        );

        ConquistaSimples mestreGeografia = new ConquistaSimples(
                "Mestre em Geografia",
                "Completou um quiz na categoria de Geografia.",
                "🌍"
        );

        // 2. Cria a conquista composta (o "grupo")
        GrupoDeConquistas mestreHumanas = new GrupoDeConquistas(
                "Mestre de Humanas",
                "Dominou as áreas de História e Geografia.",
                "🏛️"
        );

        // 3. Adiciona as conquistas simples ao grupo usando o método do padrão Composite
        mestreHumanas.adicionar(historiador);
        mestreHumanas.adicionar(mestreGeografia);

        // 4. Salva o grupo (o cascade irá salvar as conquistas simples associadas)
        conquistaRepository.save(mestreHumanas);
    }
    
    // Agrupado para melhor organização
    private void criarQuizzesDeExemplo() {
        // Quiz de Geografia
        Quiz quizGeografia = new Quiz("Capitais da Europa", "Teste os seus conhecimentos sobre as capitais europeias.", "Geografia");
        Pergunta p1Geo = new Pergunta("Qual é a capital da França?");
        p1Geo.setRespostas(new HashSet<>(Arrays.asList(new Resposta("Paris", true), new Resposta("Londres", false))));
        quizGeografia.getPerguntas().add(p1Geo);
        p1Geo.setQuiz(quizGeografia);
        quizRepository.save(quizGeografia);

        // Quiz de História
        Quiz quizHistoria = new Quiz("Revolução Francesa", "Perguntas sobre um dos eventos mais marcantes da história.", "História");
        Pergunta p1Hist = new Pergunta("Em que ano começou a Revolução Francesa?");
        p1Hist.setRespostas(new HashSet<>(Arrays.asList(new Resposta("1789", true), new Resposta("1815", false))));
        quizHistoria.getPerguntas().add(p1Hist);
        p1Hist.setQuiz(quizHistoria);
        quizRepository.save(quizHistoria);
    }
}

