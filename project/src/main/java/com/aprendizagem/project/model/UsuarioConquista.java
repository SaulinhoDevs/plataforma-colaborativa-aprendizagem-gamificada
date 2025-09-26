package com.aprendizagem.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UsuarioConquista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conquista_id", nullable = false)
    private Conquista conquista;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataConquista;

    public UsuarioConquista(Usuario usuario, Conquista conquista) {
        this.usuario = usuario;
        this.conquista = conquista;
    }
}
