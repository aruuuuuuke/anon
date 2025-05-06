package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResultDTO {
    private int usersPassed;
    private int positive;
    private int negative;
    private String summary;
}
