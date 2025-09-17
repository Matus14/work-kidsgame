package com.mathematics.kidsgame.service;

import com.mathematics.kidsgame.entity.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface QuizResultInterface {

    // Save one quiz result to database
    QuizResult saveResult(QuizResult result);

    // Get all stored results (e.g. for testing or admin)
    List<QuizResult> getAllResults();

    // Get top results by score (e.g. leaderboard)
    List<QuizResult> getTopResults();

    // Get results with optional name filter and paging
    Page<QuizResult> getResults(String name, Pageable pageable);
}
