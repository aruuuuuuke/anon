package com.example.demo.analyz;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public JsonNode analyzeAsJson(String prompt) {
        Map<String, Object> request = Map.of(
                "model", "gpt-4-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "Ты HR-аналитик. Проанализируй ответы сотрудников на опрос и выдай результаты в JSON формате. Необходиые поля: статистика(общ.колличество ответов, кол-во положительных ответов, кол-во отрицательных ответов, метки основыных проблем) и отчет(в официальнм формате, включая: название дату и полный отчет с решениями проблем на одну-две страницы)."),
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.5,
                "max_tokens", 500
        );

        return webClient.post()
                .uri(OPENAI_API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    String content = json.get("choices").get(0).get("message").get("content").asText();
                    content = content.replaceAll("(?s)```json\\s*", "").replaceAll("(?s)```", "");
                    try {
                        return objectMapper.readTree(content);
                    } catch (Exception e) {
                        throw new RuntimeException("Невалидный JSON от OpenAI", e);
                    }
                })
                .block();
    }
}
