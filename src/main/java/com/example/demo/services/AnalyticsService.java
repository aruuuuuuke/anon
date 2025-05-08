//package com.example.demo.services;
//
//import com.example.demo.analyz.OpenAiClient;
//import com.example.demo.dto.AnalyticsResultDTO;
//import com.example.demo.models.Answer;
//import com.example.demo.models.Question;
//import com.example.demo.models.Survey;
//import com.example.demo.repositories.SurveyRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AnalyticsService {
//
//    private final SurveyRepository surveyRepository;
//    private final OpenAiClient openAiClient;
//    private final ObjectMapper objectMapper; // Для преобразования строки в объект
//
//    public AnalyticsResultDTO analyzeSurvey(Long surveyId) {
//        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> new RuntimeException("Опрос не найден"));
//        StringBuilder promptBuilder = new StringBuilder();
//
//        promptBuilder.append("Опрос: ").append(survey.getTitle()).append("\n")
//                .append("Описание: ").append(survey.getDescription()).append("\n\n");
//
//        for (Question question : survey.getQuestions()) {
//            promptBuilder.append("Вопрос: ").append(question.getText()).append("\n");
//            for (Answer answer : question.getAnswers()) {
//                promptBuilder.append(" - Ответ: ").append(answer.getAnswerContent()).append("\n");
//            }
//            promptBuilder.append("\n");
//        }
//
//        // Получаем строковый ответ от OpenAiClient
//        String openAiResponse = openAiClient.analyze(promptBuilder.toString());
//
//        try {
//            // Преобразуем строку в объект AnalyticsResultDTO
//            return objectMapper.readValue(openAiResponse, AnalyticsResultDTO.class);
//        } catch (Exception e) {
//            throw new RuntimeException("Ошибка при разборе ответа от OpenAI", e);
//        }
//    }
//}
