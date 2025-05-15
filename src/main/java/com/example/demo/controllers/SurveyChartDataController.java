package com.example.demo.controllers;

import com.example.demo.models.SurveyChartData;
import com.example.demo.services.SurveyChartDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chart-data")
@RequiredArgsConstructor
public class SurveyChartDataController {

    private final SurveyChartDataService chartDataService;

    @PostMapping
    public SurveyChartData create(@RequestBody SurveyChartData chartData) {
        return chartDataService.createChartData(chartData);
    }

    @GetMapping
    public List<SurveyChartData> getAll() {
        return chartDataService.getAll();
    }

    @GetMapping("/{id}")
    public SurveyChartData getById(@PathVariable Long id) {
        return chartDataService.getById(id);
    }

    @PutMapping("/{id}")
    public SurveyChartData update(@PathVariable Long id, @RequestBody SurveyChartData updatedData) {
        return chartDataService.updateChartData(id, updatedData);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        chartDataService.deleteById(id);
    }
}
