package com.aprendizagem.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a estrutura de dados de um ranking vindo de um sistema externo.
 * Note como os nomes dos campos ("playerName", "totalScore") são diferentes
 * dos nossos ("nome", "pontos"). O nosso Adapter fará a "tradução".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalRankingEntryDTO {
    private String playerId;
    private String playerName;
    private int totalScore;
    private String countryCode; // Ex: "BR", "PT"
}
