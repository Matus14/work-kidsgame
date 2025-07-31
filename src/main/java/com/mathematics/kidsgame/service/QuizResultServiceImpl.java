package com.mathematics.kidsgame.service;


import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizResultServiceImpl implements QuizResultService {

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Override
    public QuizResult saveResult(QuizResult quizResult) {

        // This line was created to manually handle playedAt timestamp,
        // because @CreationTimestamp doest working.
        // Find out that this issue happens sometimes when object comes from a request (POST from frontend)
        quizResult.setPlayedAt(LocalDateTime.now()); // << this line added

        return quizResultRepository.save(quizResult);
    }

    @Override
    public List<QuizResult> getAllResults() {
        return quizResultRepository.findAll();
    }

    @Override
    public List<QuizResult> getTopResults(){
        return quizResultRepository.findAll();
    }

}
