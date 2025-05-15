package com.example.demo.services;

import com.example.demo.models.SurveyChartData;
import com.example.demo.models.Survey;
import com.example.demo.repositories.SurveyChartDataRepository;
import com.example.demo.repositories.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyChartDataService {

    private final SurveyChartDataRepository chartDataRepository;
    private final SurveyRepository surveyRepository;

    public SurveyChartData createChartData(SurveyChartData chartData) {
        Long surveyId = chartData.getSurvey() != null ? chartData.getSurvey().getId() : null;
        if (surveyId == null) throw new RuntimeException("Survey ID is missing");

        Survey survey = surveyRepository.findById(surveyId).orElseThrow();
        chartData.setSurvey(survey);

        return chartDataRepository.save(chartData);
    }

    public List<SurveyChartData> getAll() {
        return chartDataRepository.findAll();
    }

    public SurveyChartData getById(Long id) {
        return chartDataRepository.findById(id).orElseThrow();
    }

    public SurveyChartData updateChartData(Long id, SurveyChartData updatedData) {
        SurveyChartData existingData = chartDataRepository.findById(id).orElseThrow();

        existingData.setTotal(updatedData.getTotal());
        existingData.setPositive(updatedData.getPositive());
        existingData.setNegative(updatedData.getNegative());
        existingData.setIssues(updatedData.getIssues());

        return chartDataRepository.save(existingData);
    }

    public void deleteById(Long id) {
        chartDataRepository.deleteById(id);
    }
}
