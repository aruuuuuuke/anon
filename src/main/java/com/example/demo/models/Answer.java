package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answerContent;  // текст или вариант ответа

    @ManyToOne
    @JsonBackReference
    @NotNull
    private Question question;

    private Long selectedOptionId;  // для выбора ответа (например, multiple choice)

    @ManyToOne
    @JsonIgnore
    @NotNull
    private SurveyResponse surveyResponse;
}
