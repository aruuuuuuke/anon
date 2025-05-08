//package com.example.demo.controllers;
//
//import com.example.demo.dto.AnalyticsResultDTO;
//import com.example.demo.services.AnalyticsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/analytics")
//@RequiredArgsConstructor
//public class AnalyticsController {
//    private final AnalyticsService analyticsService;
//
//    @GetMapping("/{surveyId}")
//    public ResponseEntity<AnalyticsResultDTO> getAnalytics(@PathVariable Long surveyId) {
//        return ResponseEntity.ok(analyticsService.analyzeSurvey(surveyId));
//    }
//}
