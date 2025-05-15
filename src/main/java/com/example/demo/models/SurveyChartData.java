package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SurveyChartData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long total;         // общее количество ответов
    private Long positive;      // положительные ответы
    private Long negative;      // отрицательные ответы

    private String issues;      // проблемы (необязательное текстовое поле)

    @OneToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;      // связь с опросом
}
