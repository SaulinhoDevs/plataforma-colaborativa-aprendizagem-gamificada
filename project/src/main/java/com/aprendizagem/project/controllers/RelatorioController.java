package com.aprendizagem.project.controllers;

import com.aprendizagem.project.service.facade.RelatorioFacade;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioFacade relatorioFacade;

    public RelatorioController(RelatorioFacade relatorioFacade) {
        this.relatorioFacade = relatorioFacade;
    }

    /**
     * Exporta relatório de progresso de um quiz.
     * Exemplo: /relatorios?quizId=1&formato=pdf
     */
    @GetMapping
    public ResponseEntity<byte[]> exportarRelatorio(@RequestParam("quizId") Long quizId,
                                                    @RequestParam(value = "formato", defaultValue = "pdf") String formato) {
        try {
            byte[] dados = relatorioFacade.gerarRelatorio(quizId, formato);
            HttpHeaders headers = new HttpHeaders();
            if ("pdf".equalsIgnoreCase(formato)) {
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "relatorio_quiz_" + quizId + ".pdf");
            } else if ("csv".equalsIgnoreCase(formato)) {
                headers.setContentType(MediaType.TEXT_PLAIN);
                headers.setContentDispositionFormData("attachment", "relatorio_quiz_" + quizId + ".csv");
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "relatorio_quiz_" + quizId + ".dat");
            }
            return new ResponseEntity<>(dados, headers, HttpStatus.OK);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
