package com.aprendizagem.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // desativa CSRF (importante para permitir POST em APIs REST sem token CSRF)
                .csrf(csrf -> csrf.disable())

                // configuração de autorização
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // libera console do H2
                        .anyRequest().permitAll() // libera todas as rotas da API
                )

                // desativa tela de login
                .formLogin(form -> form.disable())

                // desativa autenticação HTTP básica
                .httpBasic(basic -> basic.disable());

        // libera uso de frames (necessário pro H2 Console funcionar)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}