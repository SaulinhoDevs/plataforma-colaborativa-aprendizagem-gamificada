package com.aprendizagem.project.usuarios.factory;

import com.aprendizagem.project.usuarios.Aluno;
import com.aprendizagem.project.usuarios.Usuario;
import com.aprendizagem.project.usuarios.enums.TipoUsuario;

public class AlunoFactory extends UsuarioFactory {
    @Override
    public Usuario criarUsuario(String nome, String email) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setTipo(TipoUsuario.ALUNO);
        aluno.setCachedTotalPoints(0);
        aluno.setNivel(1);
        return aluno;
    }
}