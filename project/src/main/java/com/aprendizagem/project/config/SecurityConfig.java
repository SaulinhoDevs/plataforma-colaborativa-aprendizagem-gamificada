package com.aprendizagem.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Permite acesso público a recursos estáticos (CSS, JS, imagens, etc.)
                .requestMatchers("/css/**", "/js/**").permitAll()
                // Permite acesso público às páginas de login e cadastro
                .requestMatchers("/login", "/cadastro").permitAll()
                // Permite acesso ao console H2 (apenas para desenvolvimento)
                .requestMatchers("/h2-console/**").permitAll()
                // Todas as outras requisições precisam de autenticação
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Define a página de login customizada
                .loginPage("/login")
                // Define a URL para onde o usuário será redirecionado após o login bem-sucedido
                .defaultSuccessUrl("/dashboard", true)
                // Permite que todos acessem a página de login
                .permitAll()
            )
            .logout(logout -> logout
                // Define a URL de logout e invalida a sessão
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // Desabilita CSRF e permite o H2 console em iframes
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
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

