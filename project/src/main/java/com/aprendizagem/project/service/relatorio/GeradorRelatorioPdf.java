package com.aprendizagem.project.service.relatorio;

import com.aprendizagem.project.model.UsuarioQuizProgresso;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Componente do subsistema de relatórios.
 * A sua única responsabilidade é gerar um relatório em formato PDF usando a biblioteca iText.
 */
@Service
public class GeradorRelatorioPdf {

    public byte[] gerar(List<UsuarioQuizProgresso> progressos) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Adiciona um título ao documento
            document.add(new Paragraph("Relatório de Desempenho do Quiz").setBold().setFontSize(18));

            // Cria uma tabela com o número de colunas
            Table table = new Table(new float[]{1, 3, 2, 2, 3});
            table.setWidth(100); // Ocupa 100% da largura da página
            table.setMarginTop(20);

            // Adiciona o cabeçalho da tabela
            table.addHeaderCell(new Cell().add(new Paragraph("ID Aluno").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Nome").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Pontos").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Acertos").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Data").setBold()));

            // Adiciona os dados de progresso à tabela
            for (UsuarioQuizProgresso progresso : progressos) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(progresso.getUsuario().getId()))));
                table.addCell(new Cell().add(new Paragraph(progresso.getUsuario().getNome())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(progresso.getPontosGanhos()))));
                table.addCell(new Cell().add(new Paragraph(progresso.getAcertos() + "/" + progresso.getTotalPerguntas())));
                table.addCell(new Cell().add(new Paragraph(progresso.getConcluidoEm().toLocalDate().toString())));
            }

            document.add(table);
            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório PDF", e);
        }
    }
}
