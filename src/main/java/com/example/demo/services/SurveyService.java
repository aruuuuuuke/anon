package com.example.demo.services;

import com.example.demo.models.Survey;
import com.example.demo.models.User;
import com.example.demo.repositories.SurveyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    public Survey updateSurvey(Long id, Survey surveyDetails) {
        Survey survey = surveyRepository.findById(id).orElseThrow();

        if (surveyDetails.getTitle() != null) {
            survey.setTitle(surveyDetails.getTitle());
        }
        if (surveyDetails.getDescription() != null) {
            survey.setDescription(surveyDetails.getDescription());
        }

        survey.setUpdatedAt(LocalDateTime.now());

        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }
}
