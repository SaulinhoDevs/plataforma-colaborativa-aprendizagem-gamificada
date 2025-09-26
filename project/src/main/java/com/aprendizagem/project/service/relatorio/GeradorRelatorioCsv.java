package com.aprendizagem.project.service.relatorio;

import com.aprendizagem.project.model.UsuarioQuizProgresso;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Componente do subsistema de relatórios.
 * A sua única responsabilidade é gerar um relatório em formato CSV.
 */
@Service
public class GeradorRelatorioCsv {

    public byte[] gerar(List<UsuarioQuizProgresso> progressos) {
        // Usa um ByteArrayOutputStream para construir o ficheiro em memória
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {

            // Escreve o cabeçalho do CSV
            writer.append("Aluno ID,Nome Aluno,Pontuacao,Total Perguntas,Data Conclusao\n");

            // Itera sobre os dados de progresso e escreve cada linha
            for (UsuarioQuizProgresso progresso : progressos) {
                writer.append(String.valueOf(progresso.getUsuario().getId())).append(",");
                writer.append(progresso.getUsuario().getNome()).append(",");
                writer.append(String.valueOf(progresso.getPontosGanhos())).append(",");
                writer.append(String.valueOf(progresso.getTotalPerguntas())).append(",");
                writer.append(progresso.getConcluidoEm().toString()).append("\n");
            }

            writer.flush();
            return baos.toByteArray();

        } catch (IOException e) {
            // Numa aplicação real, teríamos um tratamento de erro mais robusto
            throw new RuntimeException("Erro ao gerar relatório CSV", e);
        }
    }
}
