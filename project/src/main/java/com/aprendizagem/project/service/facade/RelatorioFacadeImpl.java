package com.aprendizagem.project.service.facade;

import com.aprendizagem.project.model.UsuarioQuizProgresso;
import com.aprendizagem.project.repository.UsuarioQuizProgressoRepository;
import com.aprendizagem.project.service.relatorio.GeradorRelatorioCsv;
import com.aprendizagem.project.service.relatorio.GeradorRelatorioPdf;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação concreta do padrão Facade.
 * Esta classe fornece um ponto de acesso único e simplificado para o subsistema
 * de geração de relatórios.
 */
@Service
public class RelatorioFacadeImpl implements RelatorioFacade {

    private final GeradorRelatorioCsv geradorCsv;
    private final GeradorRelatorioPdf geradorPdf;
    private final UsuarioQuizProgressoRepository progressoRepository;

    public RelatorioFacadeImpl(GeradorRelatorioCsv geradorCsv,
                               GeradorRelatorioPdf geradorPdf,
                               UsuarioQuizProgressoRepository progressoRepository) {
        this.geradorCsv = geradorCsv;
        this.geradorPdf = geradorPdf;
        this.progressoRepository = progressoRepository;
    }

    @Override
    public byte[] gerarRelatorio(Long quizId, String formato) {
        // 1. Busca os dados necessários (lógica de negócio)
        List<UsuarioQuizProgresso> progressos = progressoRepository.findByQuizId(quizId);

        // 2. Delega a chamada para o componente apropriado do subsistema
        return switch (formato.toLowerCase()) {
            case "csv" -> geradorCsv.gerar(progressos);
            case "pdf" -> geradorPdf.gerar(progressos);
            // No futuro, poderíamos adicionar outros formatos como "json"
            default -> throw new IllegalArgumentException("Formato de relatório inválido: " + formato);
        };
    }
}
