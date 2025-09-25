package com.aprendizagem.project.model.factory;

import com.aprendizagem.project.dto.CadastroRequest;
import com.aprendizagem.project.enums.TipoUsuario;
import com.aprendizagem.project.model.Aluno;
import com.aprendizagem.project.model.Professor;
import com.aprendizagem.project.model.Usuario;
import com.aprendizagem.project.model.Visitante;

public class UsuarioFactory {

    /**
     * Cria uma instância de uma subclasse de Usuário com base nos dados de um CadastroRequest.
     * Ideal para ser usado pelo UsuarioService no fluxo de cadastro real.
     *
     * @param request O objeto de transferência de dados contendo as informações do novo usuário.
     * @param passwordCriptografada A senha já processada e pronta para ser salva.
     * @return Uma instância de Aluno, Professor ou Visitante.
     */
    public static Usuario createUsuario(CadastroRequest request, String passwordCriptografada) {
        return createUsuario(
                request.getNome(),
                request.getEmail(),
                passwordCriptografada,
                request.getTipo()
        );
    }

    /**
     * Sobrecarga do método createUsuario para uso interno e em testes (como no DataInitializer).
     * É mais simples, pois não requer a criação de um DTO.
     *
     * @param nome O nome do usuário.
     * @param email O email do usuário.
     * @param password A senha (já criptografada).
     * @param tipo O tipo de usuário (Enum).
     * @return Uma instância de Aluno, Professor ou Visitante.
     */
    public static Usuario createUsuario(String nome, String email, String password, TipoUsuario tipo) {
        return switch (tipo) {
            case ALUNO -> new Aluno(nome, email, password);
            case PROFESSOR -> new Professor(nome, email, password);
            case VISITANTE -> new Visitante(nome, email, password);
        };
    }
}

