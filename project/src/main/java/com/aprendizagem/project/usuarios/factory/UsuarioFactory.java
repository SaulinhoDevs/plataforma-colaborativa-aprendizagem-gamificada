package com.aprendizagem.project.usuarios.factory;

import com.aprendizagem.project.usuarios.Usuario;

public abstract class UsuarioFactory {
    public abstract Usuario criarUsuario(String nome, String email);
}