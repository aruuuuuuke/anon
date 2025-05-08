package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalyticsResultDTO {
    @JsonProperty("статистика")
    private Statistics statistics;

    @JsonProperty("positive")
    private List<String> positive;

    @JsonProperty("negative")
    private List<String> negative;

    @JsonProperty("проблемы")
    private List<String> problems;

    @JsonProperty("отчет")
    private String report;

    // getters and setters

    public static class Statistics {
        @JsonProperty("общее количество ответов")
        private int usersPassed;

        @JsonProperty("положительные ответы")
        private int positive;

        @JsonProperty("отрицательные ответы")
        private int negative;

        @JsonProperty("ответы без мнения")
        private int neutral;

        // getters and setters
    }
}

