package com.example.demo.controllers;
//
//import com.example.demo.dto.AnswerDTO;
//import com.example.demo.dto.SurveyResponseRequest;
import com.example.demo.models.Answer;
import com.example.demo.models.SurveyResponse;
import com.example.demo.models.User;
import com.example.demo.services.SurveyResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey-responses")
public class SurveyResponseController {

    private final SurveyResponseService surveyResponseService;

    public SurveyResponseController(SurveyResponseService surveyResponseService) {
        this.surveyResponseService = surveyResponseService;
    }

    @PostMapping("/{surveyId}")
    public ResponseEntity<SurveyResponse> createSurveyResponse(@PathVariable Long surveyId,
                                                               @AuthenticationPrincipal User user,
                                                               @RequestBody List<Answer> answers) {
        SurveyResponse response = surveyResponseService.createSurveyResponse(surveyId, user.getId(), answers);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SurveyResponse>> getAllSurveyResponses() {
        List<SurveyResponse> responses = surveyResponseService.getAllSurveyResponses();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponse> getSurveyResponseById(@PathVariable Long id) {
        return surveyResponseService.getSurveyResponseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyResponse> updateSurveyResponse(@PathVariable Long id,
                                                               @RequestBody List<Answer> updatedAnswers) {
        SurveyResponse updated = surveyResponseService.updateSurveyResponse(id, updatedAnswers);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurveyResponse(@PathVariable Long id) {
        surveyResponseService.deleteSurveyResponse(id);
        return ResponseEntity.noContent().build();
    }
}
