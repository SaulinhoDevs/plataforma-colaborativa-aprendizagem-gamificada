package com.aprendizagem.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // 1. Importe a anotação

@SpringBootApplication
@EnableJpaAuditing // 2. Adicione esta anotação para ativar a auditoria
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
