package com.aprendizagem.project.service.integration;

import com.aprendizagem.project.dto.ExternalRankingEntryDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Simula um cliente que se conecta a uma API externa para buscar um ranking global.
 * Esta classe representa o "sistema legado" ou "serviço externo" que o nosso
 * padrão Adapter irá consumir e adaptar.
 */
@Service
public class ExternalRankingClient {

    /**
     * Simula uma chamada de API que retorna uma lista de jogadores de um ranking global.
     * Note que a estrutura dos dados (ExternalRankingEntryDTO) é incompatível
     * com a estrutura que o nosso dashboard espera (RankingItemDTO).
     * @return Uma lista de entradas do ranking externo.
     */
    public List<ExternalRankingEntryDTO> getGlobalRanking() {
        // Dados de exemplo, como se viessem de uma API
        System.out.println(">>> A simular chamada à API do Ranking Global...");
        return Arrays.asList(
                new ExternalRankingEntryDTO("usr-global-1", "ShadowGamer", 9800, "PT"),
                new ExternalRankingEntryDTO("usr-global-2", "CodeMaster", 9550, "BR"),
                new ExternalRankingEntryDTO("usr-global-3", "QuizNinja", 9100, "US")
        );
    }
}
