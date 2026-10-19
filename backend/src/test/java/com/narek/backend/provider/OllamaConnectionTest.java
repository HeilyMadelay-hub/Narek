package com.narek.backend.provider;


import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class OllamaConnectionTest {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void shouldConnectToOllamaAndGenerateResponse() {
        ChatClient chatClient = chatClientBuilder.build();

        String response = chatClient.prompt()
                .user("Responde únicamente con la palabra OK")
                .call()
                .content();

        assertFalse(response == null || response.isBlank());
        System.out.println("Respuesta de Ollama: " + response);
    }
}