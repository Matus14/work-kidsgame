package com.mathematics.kidsgame.service;


import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizResultService implements QuizResultInterface {

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
        return quizResultRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));
    }

    @Override
    public List<QuizResult> getTopResults(){
        return quizResultRepository.findTop10ByOrderByScoreDesc();
    }


    // Grab results from DB with optional name filter.
    // If "name" is given, do a case-insensitive search by playerName.
    // Otherwise just return all results, but still paged.
    @Override
    public Page<QuizResult> getResults(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return quizResultRepository.findByPlayerNameContainingIgnoreCase(name.trim(),pageable);
        }
        return quizResultRepository.findAll(pageable);
    }

}
