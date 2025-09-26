package com.aprendizagem.project.service.facade;

/**
 * Interface para o padrão Facade.
 * Ela fornece uma interface simplificada e unificada para um subsistema complexo
 * (neste caso, a geração de relatórios em diferentes formatos).
 *
 * O nosso Controller irá depender apenas desta interface, sem precisar de conhecer
 * os detalhes complexos de como cada tipo de relatório é gerado.
 */
public interface RelatorioFacade {

    /**
     * Gera um relatório de desempenho para um quiz específico.
     *
     * @param quizId O ID do quiz para o qual o relatório será gerado.
     * @param formato O formato desejado para o relatório (ex: "pdf", "csv").
     * @return Um array de bytes contendo os dados do relatório.
     */
    byte[] gerarRelatorio(Long quizId, String formato);
}
