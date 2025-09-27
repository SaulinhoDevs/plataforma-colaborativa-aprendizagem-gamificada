package com.aprendizagem.project.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MuralPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String autorNome;

    @Lob
    private String conteudo;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public MuralPost() {}

    public MuralPost(String autorNome, String conteudo) {
        this.autorNome = autorNome;
        this.conteudo = conteudo;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getAutorNome() { return autorNome; }
    public void setAutorNome(String autorNome) { this.autorNome = autorNome; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
