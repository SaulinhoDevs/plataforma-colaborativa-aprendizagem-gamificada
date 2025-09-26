package com.aprendizagem.project.service.integration;

import com.aprendizagem.project.dto.ExternalRankingEntryDTO;
import com.aprendizagem.project.dto.RankingItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do padrão Adapter.
 * A sua responsabilidade é adaptar a interface do ExternalRankingClient
 * para a interface que o nosso sistema espera (uma lista de RankingItemDTO).
 */
@Service
public class ExternalRankingAdapter {

    private final ExternalRankingClient externalClient;

    public ExternalRankingAdapter(ExternalRankingClient externalClient) {
        this.externalClient = externalClient;
    }

    /**
     * Busca os dados do ranking global e "traduz" cada entrada para o formato
     * que o nosso dashboard consegue entender.
     *
     * @return Uma lista de RankingItemDTO, pronta para ser usada pelo nosso sistema.
     */
    public List<RankingItemDTO> getAdaptedRanking() {
        // 1. Busca os dados no formato externo
        List<ExternalRankingEntryDTO> externalData = externalClient.getGlobalRanking();

        // 2. "Traduz" os dados para o formato interno
        return externalData.stream()
                .map(this::adapt) // Chama o método de adaptação para cada item
                .collect(Collectors.toList());
    }

    /**
     * Adapta um único objeto do formato externo para o formato interno.
     * @param externalEntry O DTO do sistema externo.
     * @return O DTO no formato que o nosso sistema entende.
     */
    private RankingItemDTO adapt(ExternalRankingEntryDTO externalEntry) {
        // Mapeia os campos: playerName -> nome, totalScore -> pontos, etc.
        return new RankingItemDTO(
                0, // A posição será definida depois no DashboardController
                externalEntry.getPlayerName(),
                externalEntry.getTotalScore(),
                "https://placehold.co/40x40/71717a/FFFFFF?text=" + externalEntry.getCountryCode(), // Avatar com o código do país
                false // Um jogador externo nunca é o "utilizador atual"
        );
    }
}
