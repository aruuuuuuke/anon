package com.example.demo.analyz;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnswerAnalyzer {

    private final List<String> positiveWords = List.of("хорошо", "отлично", "нормально", "доволен");
    private final List<String> negativeWords = List.of("плохо", "устал", "стресс", "напряженно", "недоволен");

    public boolean isPositive(String text) {
        if (text == null) return false; // или выбрось исключение, если null недопустим
        String lower = text.toLowerCase();
        return positiveWords.stream().anyMatch(lower::contains);
    }

    public String generateSummary(List<String> answers) {
        boolean fatigue = answers.stream()
                .anyMatch(a -> a != null && a.toLowerCase().contains("устал"));

        boolean stress = answers.stream()
                .anyMatch(a -> a != null && a.toLowerCase().contains("стресс"));

        StringBuilder summary = new StringBuilder("на основе данного опроса выявлены проблемы: ");
        if (fatigue) summary.append("усталость, ");
        if (stress) summary.append("стресс, ");
        summary.append("рекомендуется обратить внимание на мотивацию и условия труда.");
        return summary.toString();
    }
}

