package com.aprendizagem.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para transferir dados do usuário para a view de forma segura
@Data
@NoArgsConstructor
public class UsuarioDTO {
    private String nome;
    private String email;
    private String avatarUrl; // Campo que estava faltando
    private String nivelDescricao;
}
