package com.mathematics.kidsgame.controller;


import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.service.QuizResultInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
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

    // POST: save one quiz result
    // take the full object from frontend (@RequestBody),
    // push it through the service to save it in DB,
    // and return 201 Created with the saved object (now it has id + playedAt).
    @PostMapping
    public ResponseEntity<QuizResult> saveResult(@RequestBody final QuizResult result) {
        QuizResult saved = quizResultInterface.saveResult(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



    // GET: pageable list of results
    // GET -> paged results
    // ?page=0 (first page), ?size=10 (rows per page), ?sort=score,desc (order by score high > low)
    // Response is a Page: has "content" + extra info like totalPages, totalElements
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
