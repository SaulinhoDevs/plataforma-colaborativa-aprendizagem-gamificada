package com.aprendizagem.project.usuarios.factory;

import com.aprendizagem.project.usuarios.Usuario;
import com.aprendizagem.project.usuarios.Visitante;
import com.aprendizagem.project.enums.TipoUsuario;

public class VisitanteFactory extends UsuarioFactory {
    @Override
    public Usuario criarUsuario(String nome, String email) {
        Visitante v = new Visitante();
        v.setNome(nome);
        v.setEmail(email);
        v.setTipo(TipoUsuario.VISITANTE);
        v.setConvidadoEspecial(false);
        return v;
    }
}