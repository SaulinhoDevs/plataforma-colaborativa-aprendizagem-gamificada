package com.aprendizagem.project.config;

import com.aprendizagem.project.usuarios.Aluno;
import com.aprendizagem.project.usuarios.Professor;
import com.aprendizagem.project.usuarios.Usuario;
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
        Usuario u1 = new Aluno();
        u1.setNome("Maria");
        u1.setEmail("maria@email.com");

        Usuario u2 = new Professor();
        u2.setNome("Alex");
        u2.setEmail("alex@email.com");

        usuarioRepository.saveAll(Arrays.asList(u1, u2));
    }
}