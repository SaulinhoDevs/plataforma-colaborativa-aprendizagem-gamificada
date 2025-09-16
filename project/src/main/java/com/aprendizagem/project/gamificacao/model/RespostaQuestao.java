package com.aprendizagem.project.gamificacao.model;

import com.aprendizagem.project.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RespostaQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Questao questao;

    @ManyToOne
    private Usuario aluno;

    private boolean correta;
    private long tempoMs; // tempo gasto na questão
}