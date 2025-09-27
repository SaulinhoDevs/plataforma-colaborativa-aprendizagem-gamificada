package com.aprendizagem.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResumoDTO {
    private Long id;
    private String titulo;
    private String categoria;
    private int progresso; // Percentual de 0-100
    
    // NOVOS CAMPOS para melhor controle de estado:
    private StatusQuiz status; // NOVO, EM_PROGRESSO, CONCLUIDO
    private Integer pontuacaoObtida; // Pontos ganhos se concluído
    private String tempoEstimado; // Tempo estimado para completar
    
    /**
     * Enum para representar o status do quiz para o usuário
     */
    public enum StatusQuiz {
        NOVO("Novo"),
        EM_PROGRESSO("Em Progresso"), 
        CONCLUIDO("Concluído");
        
        private final String descricao;
        
        StatusQuiz(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // CONSTRUTORES ADICIONAIS para facilitar criação:
    
    /**
     * Construtor para quiz novo (nunca tentado)
     */
    public QuizResumoDTO(Long id, String titulo, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.progresso = 0;
        this.status = StatusQuiz.NOVO;
        this.pontuacaoObtida = null;
        this.tempoEstimado = "~5 min"; // Valor padrão
    }
    
    /**
     * Construtor para quiz concluído
     */
    public QuizResumoDTO(Long id, String titulo, String categoria, int pontuacaoObtida) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.progresso = 100;
        this.status = StatusQuiz.CONCLUIDO;
        this.pontuacaoObtida = pontuacaoObtida;
        this.tempoEstimado = null; // Já foi concluído
    }
    
    // MÉTODOS UTILITÁRIOS:
    
    /**
     * Verifica se o quiz é novo para o usuário
     */
    public boolean isNovo() {
        return status == StatusQuiz.NOVO;
    }
    
    /**
     * Verifica se o quiz está em progresso
     */
    public boolean isEmProgresso() {
        return status == StatusQuiz.EM_PROGRESSO;
    }
    
    /**
     * Verifica se o quiz foi concluído
     */
    public boolean isConcluido() {
        return status == StatusQuiz.CONCLUIDO;
    }
    
    /**
     * Retorna a cor da barra de progresso baseada no status
     */
    public String getCorProgresso() {
        return switch (status) {
            case NOVO -> "bg-secondary";
            case EM_PROGRESSO -> "bg-warning";
            case CONCLUIDO -> "bg-success";
        };
    }
    
    /**
     * Retorna o texto descritivo do progresso
     */
    public String getTextoProgresso() {
        return switch (status) {
            case NOVO -> "Começar";
            case EM_PROGRESSO -> progresso + "% completo";
            case CONCLUIDO -> "Concluído - " + pontuacaoObtida + " pts";
        };
    }
    
    /**
     * Atualiza o status baseado no progresso
     */
    public void atualizarStatus() {
        if (progresso == 0) {
            this.status = StatusQuiz.NOVO;
        } else if (progresso == 100) {
            this.status = StatusQuiz.CONCLUIDO;
        } else {
            this.status = StatusQuiz.EM_PROGRESSO;
        }
    }
    
    /**
     * Factory method para criar quiz com progresso específico
     */
    public static QuizResumoDTO comProgresso(Long id, String titulo, String categoria, int progresso, Integer pontuacao) {
        QuizResumoDTO dto = new QuizResumoDTO();
        dto.setId(id);
        dto.setTitulo(titulo);
        dto.setCategoria(categoria);
        dto.setProgresso(progresso);
        dto.setPontuacaoObtida(pontuacao);
        dto.setTempoEstimado(progresso == 100 ? null : "~" + (5 - (progresso * 5 / 100)) + " min");
        dto.atualizarStatus();
        return dto;
    }
}