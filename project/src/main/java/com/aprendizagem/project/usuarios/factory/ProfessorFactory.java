package com.aprendizagem.project.usuarios.factory;

import com.aprendizagem.project.usuarios.Professor;
import com.aprendizagem.project.usuarios.Usuario;

public class ProfessorFactory extends UsuarioFactory {
    @Override
    public Usuario criarUsuario(String nome, String email) {
        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setEmail(email);
        professor.setDisciplina("A definir");
        return professor;
    }
}