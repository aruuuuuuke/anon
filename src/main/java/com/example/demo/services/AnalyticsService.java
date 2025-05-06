package com.example.demo.services;

import com.example.demo.analyz.AnswerAnalyzer;
import com.example.demo.dto.AnalyticsResultDTO;
import com.example.demo.models.Answer;
import com.example.demo.models.SurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final SurveyService surveyService;
    private final AnswerAnalyzer answerAnalyzer;

    public AnalyticsResultDTO analyzeSurvey(Long surveyId) {
        List<SurveyResponse> responses = surveyService.getResponsesBySurveyId(surveyId);
        int usersPassed = responses.size();

        int positive = 0;
        int negative = 0;
        List<String> allAnswers = new ArrayList<>();

        for (SurveyResponse response : responses) {
            for (Answer answer : response.getAnswers()) {
                String content = answer.getAnswerContent();
                allAnswers.add(content);
                if (answerAnalyzer.isPositive(content)) positive++;
                else negative++;
            }
        }

        String summary = answerAnalyzer.generateSummary(allAnswers);

        return new AnalyticsResultDTO(usersPassed, positive, negative, summary);
    }
}
