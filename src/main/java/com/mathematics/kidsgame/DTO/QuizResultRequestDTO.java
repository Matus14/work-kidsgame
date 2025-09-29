package com.mathematics.kidsgame.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;




@Data
public class QuizResultRequestDTO {


    @NotBlank
    @Column(nullable = false, length = 30)
    private String playerName;


    @Min(0)
    private int correctAnswer;

    @Min(0)
    private int incorrectAnswer;

    @Min(0)
    private int durationSeconds;

    @Min(0)
    private int score;

}
