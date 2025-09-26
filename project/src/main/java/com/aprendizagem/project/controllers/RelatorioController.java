package com.aprendizagem.project.controllers;

import com.aprendizagem.project.service.facade.RelatorioFacade;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioFacade relatorioFacade;

    public RelatorioController(RelatorioFacade relatorioFacade) {
        this.relatorioFacade = relatorioFacade;
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<byte[]> baixarRelatorioQuiz(
            @PathVariable Long quizId,
            @RequestParam String formato) {

        try {
            byte[] dados = relatorioFacade.gerarRelatorio(quizId, formato);

            HttpHeaders headers = new HttpHeaders();
            String nomeFicheiro = "relatorio_quiz_" + quizId + "." + formato;

            // Define o tipo de conteúdo e o cabeçalho para forçar o download
            headers.setContentDispositionFormData("attachment", nomeFicheiro);

            if ("pdf".equalsIgnoreCase(formato)) {
                headers.setContentType(MediaType.APPLICATION_PDF);
            } else if ("csv".equalsIgnoreCase(formato)) {
                headers.setContentType(MediaType.TEXT_PLAIN);
            }

            return new ResponseEntity<>(dados, headers, HttpStatus.OK);

        } catch (Exception e) {
            // Se houver um erro, retorna uma resposta de erro
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
