package com.aprendizagem.project.model.factory;

import com.aprendizagem.project.enums.TipoUsuario;
import com.aprendizagem.project.model.Aluno;
import com.aprendizagem.project.model.Professor;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.Visitante;

public class UsuarioFactory {

    public static Usuario criarUsuario(TipoUsuario tipo, String nome, String email, String senha) {
        return switch (tipo) {
            case ALUNO -> new Aluno(nome, email, senha);
            case PROFESSOR -> new Professor(nome, email, senha);
            case VISITANTE -> new Visitante(nome, email, senha);
        };
    }
}