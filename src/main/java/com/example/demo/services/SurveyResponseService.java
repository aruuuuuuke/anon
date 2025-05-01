package com.example.demo.services;

import com.example.demo.dto.SurveyResponseRequest;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyResponseService {

    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    public SurveyResponseService(SurveyResponseRepository surveyResponseRepository,
                                 SurveyRepository surveyRepository,
                                 UserRepository userRepository) {
        this.surveyResponseRepository = surveyResponseRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    public SurveyResponse createSurveyResponse(Long surveyId, Long userId, List<Answer> answers) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setSurvey(survey);
        surveyResponse.setUser(user);

        for (Answer answer : answers) {
            Question q = answer.getQuestion();
            if (q != null && q.getId() != null) {
                Question foundQuestion = survey.getQuestions().stream()
                        .filter(qq -> qq.getId().equals(q.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Вопрос не найден"));

                answer.setQuestion(foundQuestion);
            } else {
                throw new RuntimeException("Ответ не содержит ID вопроса");
            }

            answer.setSurveyResponse(surveyResponse); // связываем ответ с откликом
        }

        surveyResponse.setAnswers(answers);

        return surveyResponseRepository.save(surveyResponse);
    }


    public List<SurveyResponse> getAllSurveyResponses() {
        return surveyResponseRepository.findAll();
    }

    public Optional<SurveyResponse> getSurveyResponseById(Long id) {
        return surveyResponseRepository.findById(id);
    }

    public SurveyResponse updateSurveyResponse(Long id, List<Answer> updatedAnswers) {
        SurveyResponse surveyResponse = surveyResponseRepository.findById(id).orElseThrow();
        surveyResponse.setAnswers(updatedAnswers);

        for (Answer answer : updatedAnswers) {
            Question question = answer.getQuestion();
            answer.setQuestion(question);
        }

        return surveyResponseRepository.save(surveyResponse);
    }

    public void deleteSurveyResponse(Long id) {
        surveyResponseRepository.deleteById(id);
    }
}
