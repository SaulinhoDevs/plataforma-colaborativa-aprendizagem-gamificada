package com.aprendizagem.project.model.factory;

import com.aprendizagem.project.dto.CadastroRequest;
import com.aprendizagem.project.enums.TipoUsuario;
import com.aprendizagem.project.model.Aluno;
import com.aprendizagem.project.model.Professor;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.Visitante; // Adicionado para completude, se necessário

public class UsuarioFactory {

    /**
     * Cria uma instância de Usuário (Aluno, Professor, etc.) a partir de um CadastroRequest.
     * A senha já deve vir criptografada do service.
     *
     * @param request           O DTO com os dados do formulário de cadastro.
     * @param encryptedPassword A senha já processada pelo PasswordEncoder.
     * @return Uma nova instância de Aluno ou Professor.
     */
    public static Usuario createUsuario(CadastroRequest request, String encryptedPassword) {
        TipoUsuario tipo = request.getTipo();
        String nome = request.getNome();
        String email = request.getEmail();

        return switch (tipo) {
            case ALUNO -> new Aluno(nome, email, encryptedPassword);
            case PROFESSOR -> new Professor(nome, email, encryptedPassword);
            case VISITANTE -> new Visitante(nome, email, encryptedPassword);
            default ->
                    throw new IllegalArgumentException("Tipo de usuário inválido para cadastro: " + tipo);
        };
    }
}

