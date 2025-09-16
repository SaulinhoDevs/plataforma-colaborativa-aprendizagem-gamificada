package com.aprendizagem.project.usuarios.factory;

import com.aprendizagem.project.usuarios.Professor;
import com.aprendizagem.project.usuarios.Usuario;
import com.aprendizagem.project.usuarios.enums.TipoUsuario;

public class ProfessorFactory extends UsuarioFactory {
    @Override
    public Usuario criarUsuario(String nome, String email) {
        Professor p = new Professor();
        p.setNome(nome);
        p.setEmail(email);
        p.setTipo(TipoUsuario.PROFESSOR);
        p.setDisciplina("A definir");
        return p;
    }
}