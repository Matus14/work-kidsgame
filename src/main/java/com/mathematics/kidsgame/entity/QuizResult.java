package com.mathematics.kidsgame.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Data // Lombok annotation which automatically handle the getters/setters, toString, equals, hashCode
@Entity // This marks this class as Entity to enable mapping into database table
@NoArgsConstructor // Creates nonparametric constructor for Hibernate
@AllArgsConstructor // Creates normal constructor
public class QuizResult {

    @Id // Marks this as primary key in the database table
    @GeneratedValue(strategy = GenerationType.AUTO) // Creates unique ID for each newly added row/data
    private Long id;

    private String playerName;

    private int correctAnswer;
    private int incorrectAnswer;
    private int durationSeconds;
    private int score;

    private LocalDateTime playedAt;

}