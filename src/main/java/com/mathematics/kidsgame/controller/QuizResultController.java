package com.mathematics.kidsgame.controller;


import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.repository.QuizResultRepository;
import com.mathematics.kidsgame.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/results")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;


    @PostMapping
    public QuizResult saveResult(@RequestBody final QuizResult result) {
        return quizResultService.saveResult(result);
    }

    @GetMapping
    public List<QuizResult> getAllResults() {
        return quizResultService.getAllResults();
    }

    @GetMapping("/top")
    public List<QuizResult> getTopResults() {
        return quizResultService.getTopResults();
    }


}
