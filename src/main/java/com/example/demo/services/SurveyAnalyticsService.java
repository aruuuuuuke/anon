package com.example.demo.services;

import com.example.demo.analyz.OpenAiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.demo.models.Survey;
import com.example.demo.models.SurveyResponse;
import com.example.demo.repositories.SurveyRepository;
import com.example.demo.repositories.SurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyAnalyticsService {

    private final SurveyRepository surveyRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final OpenAiClient openAiClient;

    public JsonNode analyzeSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Опрос не найден"));

        List<SurveyResponse> responses = surveyResponseRepository.findBySurveyId(surveyId);

        StringBuilder prompt = new StringBuilder();
        prompt.append("Опрос: ").append(survey.getTitle()).append("\n");
        prompt.append("Описание: ").append(survey.getDescription()).append("\n\n");

        for (SurveyResponse response : responses) {
            prompt.append("Ответ пользователя:\n");
            response.getAnswers().forEach(answer -> {
                prompt.append("Вопрос: ").append(answer.getQuestion().getText()).append("\n");
                prompt.append("Ответ: ").append(answer.getAnswerContent()).append("\n\n");
            });
        }

        // Передаем собранный prompt в OpenAiClient для анализа и возвращаем JsonNode
        return openAiClient.analyzeAsJson(prompt.toString());
    }
}
