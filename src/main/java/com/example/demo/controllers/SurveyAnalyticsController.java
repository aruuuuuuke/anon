package com.example.demo.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.example.demo.services.SurveyAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class SurveyAnalyticsController {

    private final SurveyAnalyticsService surveyAnalyticsService;

    @GetMapping(value = "/survey/{id}", produces = "application/json")
    public JsonNode analyzeSurvey(@PathVariable Long id) {
        return surveyAnalyticsService.analyzeSurvey(id);
    }
}
