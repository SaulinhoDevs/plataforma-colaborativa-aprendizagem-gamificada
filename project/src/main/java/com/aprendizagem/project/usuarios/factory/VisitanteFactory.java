package com.aprendizagem.project.usuarios.factory;

import com.aprendizagem.project.usuarios.Usuario;
import com.aprendizagem.project.usuarios.Visitante;

public class VisitanteFactory extends UsuarioFactory {
    @Override
    public Usuario criarUsuario(String nome, String email) {
        Visitante visitante = new Visitante();
        visitante.setNome(nome);
        visitante.setEmail(email);
        visitante.setConvidadoEspecial(false);
        return visitante;
    }
}