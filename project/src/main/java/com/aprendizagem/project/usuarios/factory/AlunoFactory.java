package com.aprendizagem.project.usuarios.factory;

import com.aprendizagem.project.usuarios.Aluno;
import com.aprendizagem.project.usuarios.Usuario;

public class AlunoFactory extends UsuarioFactory {
    @Override
    public Usuario criarUsuario(String nome, String email) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setPontos(0);
        aluno.setNivel(1);
        return aluno;
    }
}