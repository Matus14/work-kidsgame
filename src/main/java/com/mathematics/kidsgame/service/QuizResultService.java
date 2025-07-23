package com.mathematics.kidsgame.service;

import com.mathematics.kidsgame.entity.QuizResult;

import java.util.List;

public interface QuizResultService {

    // Save one quiz result to database
    QuizResult saveResult(QuizResult result);

    // Get all stored results (e.g. for testing or admin)
    List<QuizResult> getAllResults();

    // Get top results by score (e.g. leaderboard)
    List<QuizResult> getTopResults();
}
