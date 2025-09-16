package com.aprendizagem.project.config;

import com.aprendizagem.project.usuarios.Usuario;
import com.aprendizagem.project.usuarios.factory.AlunoFactory;
import com.aprendizagem.project.usuarios.factory.ProfessorFactory;
import com.aprendizagem.project.usuarios.factory.VisitanteFactory;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public SeedConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario u1 = new AlunoFactory().criarUsuario("Maria Brown", "maria@example.com");
            Usuario u2 = new ProfessorFactory().criarUsuario("Alex Green", "alex@example.com");
            Usuario u3 = new VisitanteFactory().criarUsuario("Visitante X", "visitante@example.com");

            usuarioRepository.save(u1);
            usuarioRepository.save(u2);
            usuarioRepository.save(u3);
            System.out.println("Seed: 3 usuários criados.");
        }
    }
}