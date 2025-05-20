package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.SurveyRepository;
import com.example.demo.repositories.SurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SurveyService {

    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyRepository surveyRepository;


    public SurveyService(SurveyRepository surveyRepository, SurveyResponseRepository surveyResponseRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyResponseRepository = surveyResponseRepository;
    }

    public Survey createSurvey(Survey survey, User user) {
        survey.setCreatedBy(user);

        for (Question question : survey.getQuestions()) {
            question.setSurvey(survey);
            for (Option option : question.getOptions()) {
                option.setQuestion(question);
            }
        }
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys(User user) {
        String managerCode = user.getAssignedManagerCode();
        if (managerCode == null) {
            return List.of(); // пользователь не привязан — возвращаем пусто
        }
        return surveyRepository.findByCreatedBy_ManagerCode(managerCode);
    }


    public Optional<Survey> getSurveyById(Long id) {

        return surveyRepository.findById(id);
    }

    public Survey updateSurvey(Long id, Survey surveyDetails) {
        Survey survey = surveyRepository.findById(id).orElseThrow();
        survey.setTitle(surveyDetails.getTitle());
        survey.setDescription(surveyDetails.getDescription());
        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }
    public List<SurveyResponse> getResponsesBySurveyId(Long surveyId) {
        return surveyResponseRepository.findBySurveyId(surveyId);

    }
}