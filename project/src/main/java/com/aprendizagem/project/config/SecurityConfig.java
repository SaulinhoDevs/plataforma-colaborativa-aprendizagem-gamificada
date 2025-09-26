package com.aprendizagem.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita anotações de segurança a nível de método, como @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público a recursos estáticos
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        // Permite acesso público às páginas de entrada
                        .requestMatchers("/", "/index", "/login", "/cadastro").permitAll()
                        // Permite acesso ao console H2 (apenas para desenvolvimento)
                        .requestMatchers("/h2-console/**").permitAll()

                        // --- REGRAS DE ACESSO POR FUNÇÃO ---
                        // Apenas utilizadores com a role 'PROFESSOR' podem aceder a estas páginas
                        .requestMatchers("/gestao-quizzes", "/criar-quiz", "/relatorio/**").hasRole("PROFESSOR")
                        // Apenas utilizadores com a role 'ALUNO' podem aceder a estas páginas
                        .requestMatchers("/quiz/**", "/quiz-resultado").hasRole("ALUNO")

                        // Todas as outras requisições (como /dashboard e /perfil) precisam apenas de autenticação
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        // Permite POSTs para o endpoint de submissão do quiz e para o H2 console
                        .ignoringRequestMatchers("/quiz/submit", "/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(options -> options.sameOrigin())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

