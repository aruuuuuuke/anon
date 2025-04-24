package com.example.demo.services;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;

public class DeepseekService {

    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "your_deepseek_api_key";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String analyzeSurvey(String surveyText) throws IOException {
        Map<String, Object> systemMessage = Map.of(
                "role", "system",
                "content", "Ты — профессиональный психолог и дата-аналитик. Отвечай вежливо и структурированно. " +
                        "Твоя задача — анализировать данные опросов сотрудников, которые оценивают обстановку в коллективе. " +
                        "На основе текста опросника и ответов выявляй: " +
                        "- повторяющиеся паттерны поведения, " +
                        "- числовые показатели (если есть), " +
                        "- возможные проблемы в коллективе, " +
                        "- пути решения этих проблем. " +
                        "Ответ формируй в виде структурированного отчета. Отвечай только на русском!"
        );

        Map<String, Object> userMessage = Map.of(
                "role", "user",
                "content", surveyText + " Отвечай только на русском!"
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", "deepseek-chat");
        body.put("messages", List.of(systemMessage, userMessage));

        RequestBody requestBody = RequestBody.create(
                mapper.writeValueAsString(body),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            Map<?, ?> json = mapper.readValue(responseBody, Map.class);
            List<?> choices = (List<?>) json.get("choices");
            Map<?, ?> choice = (Map<?, ?>) choices.get(0);
            Map<?, ?> message = (Map<?, ?>) choice.get("message");
            return (String) message.get("content");
        }
    }
}
