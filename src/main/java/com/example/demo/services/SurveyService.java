package com.example.demo.services;

import com.example.demo.models.Option;
import com.example.demo.models.Question;
import com.example.demo.models.Survey;
import com.example.demo.models.User;
import com.example.demo.repositories.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
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


    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
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
}