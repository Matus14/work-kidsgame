package com.mathematics.kidsgame.controller;


import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.service.QuizResultInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/results")
public class QuizResultController {

    @Autowired
    private QuizResultInterface quizResultInterface;


    @PostMapping
    public ResponseEntity<QuizResult> saveResult(@RequestBody final QuizResult result) {
        QuizResult saved = quizResultInterface.saveResult(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public Page<QuizResult> getResults(
            @RequestParam(required = false) String name, Pageable pageable
    ) {
        return quizResultInterface.getResults(name,pageable);
    }

    @GetMapping("/top")
    public List<QuizResult> getTopResults() {
        return quizResultInterface.getTopResults();
    }


}
