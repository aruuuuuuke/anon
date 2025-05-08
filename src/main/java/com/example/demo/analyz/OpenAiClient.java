package com.example.demo.analyz;

import com.example.demo.dto.AnalyticsResultDTO;
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

    // URL для OpenAI API
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public AnalyticsResultDTO analyze(String prompt) {
        // Подготовка данных для запроса
        Map<String, Object> request = Map.of(
                "model", "gpt-4-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "Ты HR-аналитик. Проанализируй ответы сотрудников на опрос и выдай результаты в JSON формате. Структура: статистика, positive, negative, проблемы, и отчет."),
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.1,
                "max_tokens", 200
        );

        // Отправка запроса в OpenAI
        String response = webClient.post()
                .uri(OPENAI_API_URL) // Указываем API URL
                .header("Authorization", "Bearer " + apiKey) // добавляем токен OpenAI
                .bodyValue(request)
                .retrieve()
                .bodyToMono(JsonNode.class)  // Получаем JSON-объект
                .map(json -> {
                    // Логируем весь ответ от OpenAI
                    String responseText = json.get("choices").get(0).get("message").get("content").asText();
                    System.out.println("Response from OpenAI: " + responseText);
                    responseText = responseText.replaceAll("(?s)```json\\s*", ""); // удаляет открывающий тег ```json с переводом строки
                    responseText = responseText.replaceAll("(?s)```", "");         // удаляет закрывающий тег ```

// Теперь строка должна быть валидным JSON
                    return responseText;
                })
                .block();
        try {
            // Преобразование JSON-ответа в DTO
            System.out.println("Cleaned response: " + response);
            return objectMapper.readValue(response, AnalyticsResultDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при парсинге ответа от OpenAI", e);
        }
    }
}
