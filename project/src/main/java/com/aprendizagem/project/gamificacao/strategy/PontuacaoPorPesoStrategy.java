package com.aprendizagem.project.gamificacao.strategy;

import com.aprendizagem.project.gamificacao.model.ParticipacaoDesafio;
import com.aprendizagem.project.gamificacao.model.Questao;

public class PontuacaoPorPesoStrategy implements PontuacaoStrategy {

    @Override
    public int calcularPontuacao(ParticipacaoDesafio participacao) {
        var desafio = participacao.getDesafio();
        int somaPesos = desafio.getQuestoes().stream().mapToInt(Questao::getPeso).sum();

        int pontos = 0;
        int streak = 0;
        int maxStreak = 0;
        int acertosPesados = 0;
        long tempoTotal = 0;

        for (var resposta : participacao.getRespostas()) {
            tempoTotal += resposta.getTempoMs();
            if (resposta.isCorreta()) {
                int peso = resposta.getQuestao().getPeso();
                int pontosQuestao = (peso * desafio.getNotaMaxima()) / somaPesos;
                pontos += pontosQuestao;
                streak++;
                if (peso > 5) acertosPesados++;
                if (streak > maxStreak) maxStreak = streak;
            } else {
                streak = 0; // reset da sequência
            }
        }

        participacao.setPontos(pontos);
        participacao.setTempoTotalMs(tempoTotal);
        participacao.setMaiorSequenciaAcertos(maxStreak);
        participacao.setAcertosPesados(acertosPesados);

        return pontos;
    }
}