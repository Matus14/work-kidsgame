package com.mathematics.kidsgame.service;

import com.mathematics.kidsgame.DTO.QuizResultRequestDTO;
import com.mathematics.kidsgame.DTO.QuizResultResponseDTO;
import com.mathematics.kidsgame.entity.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface QuizResultInterface {

    // Save one quiz result to database
    QuizResultResponseDTO saveResult(QuizResultRequestDTO request);

    // Get all stored results (e.g. for testing or admin)
    List<QuizResultResponseDTO> getAllResults();

    // Get top results by score (e.g. leaderboard)
    List<QuizResultResponseDTO> getTopResults();

    // Get results with optional name filter and paging
    Page<QuizResultResponseDTO> getResults(String name, Pageable pageable);

    QuizResultResponseDTO findById(Long id);

    void delete(Long id);


}
