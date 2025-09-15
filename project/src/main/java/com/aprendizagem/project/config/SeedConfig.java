package com.aprendizagem.project.config;

import com.aprendizagem.project.usuarios.factory.AlunoFactory;
import com.aprendizagem.project.usuarios.factory.ProfessorFactory;
import com.aprendizagem.project.usuarios.factory.UsuarioFactory;
import com.aprendizagem.project.usuarios.factory.VisitanteFactory;
import com.aprendizagem.project.usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SeedConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public SeedConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        UsuarioFactory factory1 = new AlunoFactory();
        UsuarioFactory factory2 = new ProfessorFactory();
        UsuarioFactory factory3 = new VisitanteFactory();

        usuarioRepository.saveAll(Arrays.asList(factory1.criarUsuario("Maria Green", "maria@gmail.com"),
                factory2.criarUsuario("Bob Brown", "bob@gmail.com"),
                factory3.criarUsuario("Rosa Pink", "rosa@gmail.com")));
    }
}