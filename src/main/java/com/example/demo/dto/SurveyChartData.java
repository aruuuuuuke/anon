package com.example.demo.dto;

import com.example.demo.models.Survey;

public class SurveyChartData {
    private Long id;
    private int total;
    private int positive;
    private int negative;
    private String issues;
    private Survey survey; // важно: этот объект должен быть с id
}
