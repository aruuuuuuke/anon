package com.example.demo.repositories;

import com.example.demo.models.SurveyChartData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyChartDataRepository extends JpaRepository<SurveyChartData, Long> {
    SurveyChartData findBySurveyId(Long surveyId);
}
