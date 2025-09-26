package com.aprendizagem.project.config;

import com.aprendizagem.project.enums.TipoUsuario;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.factory.UsuarioFactory;
import com.aprendizagem.project.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * ATUALIZADO: Esta classe agora tem a única responsabilidade de garantir
 * que um utilizador 'admin' exista para facilitar os testes durante o desenvolvimento.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Cria o utilizador admin apenas se ele não existir
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = UsuarioFactory.createUsuario(
                    "Admin",
                    "admin@admin.com",
                    passwordEncoder.encode("admin123"),
                    TipoUsuario.PROFESSOR
            );
            usuarioRepository.save(admin);
            System.out.println(">>> Utilizador 'admin' de teste criado com sucesso!");
        }
    }
}

