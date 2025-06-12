package br.com.rangel.music.screensound.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGemini {

    public static String obterInformacao(String texto) {
        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey("SUA CHAVE")
                .modelName("gemini-1.5-flash")
                .maxOutputTokens(200)
                .temperature(0.5)
                .build();

        String response = gemini.generate("me de um breve resumo sobre o artista: " + texto);
        return response;
    }

}
