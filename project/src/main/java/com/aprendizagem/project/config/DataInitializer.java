package com.aprendizagem.project.config;

import com.aprendizagem.project.model.Professor;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o usuário admin já existe para não criar duplicatas
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            
            // Cria um novo usuário Professor
            Usuario admin = new Professor(
                    "Admin",
                    "admin@admin.com",
                    passwordEncoder.encode("admin123") // Criptografa a senha
            );

            // Salva o usuário no banco de dados
            usuarioRepository.save(admin);
            
            System.out.println(">>> Usuário admin de teste criado com sucesso!");
            // Dentro do método run() em DataInitializer.java
            System.out.println("SENHA CRIPTOGRAFADA PARA H2: " + passwordEncoder.encode("admin123"));
        }
    }
}
