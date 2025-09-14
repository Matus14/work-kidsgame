package com.mathematics.kidsgame.repository;

import com.mathematics.kidsgame.entity.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    // This method uses Spring Data JPA "query method naming convention".
    // That means we don’t need to write any SQL – Spring will generate the query automatically
    //
    // Let's break down the method name:
    // - findTop10 → limits the result to 10 records
    // - ByOrderByScoreDesc → sorts the results by the "score" field, descending (highest first)
    //
    // So basically: this will fetch the top 10 results from the database, ordered by score from highest to lowest
    //
    // The method name is like a pattern/template – Spring reads it and builds the correct SQL query behind the scenes
    List<QuizResult> findTop10ByOrderByScoreDesc();

    Page<QuizResult> findByPlayerNameContainingIgnoreCase(String name, Pageable pageable);

}
