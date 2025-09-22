package com.aprendizagem.project.model;

import com.aprendizagem.project.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Sugestões Aplicadas:
 * 1.  Lombok (@Data, @NoArgsConstructor): Reduz código boilerplate de getters, setters e construtores.
 * 2.  Spring Data JPA Auditing: Automatiza as datas de criação/atualização.
 * 3.  Renomeação de "senha" para "password": Alinha com as convenções do Spring Security.
 * 4.  Otimização da Coluna de Tipo: Evita a criação de uma coluna redundante no banco.
 */
@Entity
@Data // Gera Getters, Setters, equals, hashCode e toString
@NoArgsConstructor // Gera um construtor sem argumentos
@EntityListeners(AuditingEntityListener.class) // Habilita a auditoria automática do Spring
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_USUARIO", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Renomeado para "password" para facilitar a integração com o Spring Security

    // Este campo agora apenas lê o valor da coluna discriminadora, sem criar uma coluna duplicada.
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_USUARIO", insertable = false, updatable = false, nullable = false)
    private TipoUsuario tipo;

    @CreatedDate // O Spring vai gerenciar esta data automaticamente
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // O Spring vai gerenciar esta data automaticamente
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Construtor principal para as subclasses
    public Usuario(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }
}

