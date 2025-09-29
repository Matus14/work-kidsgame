package com.mathematics.kidsgame.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QuizResultResponseDTO {


    private Long id;
    private String playerName;
    private int correctAnswer;
    private int incorrectAnswer;
    private int durationSeconds;
    private int score;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime playedAt;
}
