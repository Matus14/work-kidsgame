package com.mathematics.kidsgame.controller;


import com.mathematics.kidsgame.DTO.QuizResultRequestDTO;
import com.mathematics.kidsgame.DTO.QuizResultResponseDTO;
import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.service.QuizResultInterface;
import jakarta.validation.Valid;
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
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResultResponseDTO saveResult(@Valid @RequestBody QuizResultRequestDTO request){
        return quizResultInterface.saveResult(request);
    }

    @GetMapping("/all")
    public List<QuizResultResponseDTO> findAll() {
        return quizResultInterface.getAllResults();
    }

    @GetMapping("/{id}")
    public QuizResultResponseDTO findById(@PathVariable Long id) {
        return quizResultInterface.findById(id);
    }


    // GET: pageable list of results
    // GET -> paged results
    // ?page=0 (first page), ?size=10 (rows per page), ?sort=score,desc (order by score high > low)
    // Response is a Page: has "content" + extra info like totalPages, totalElements
    @GetMapping
    public Page<QuizResultResponseDTO> getResults(
            @RequestParam(required = false) String name, Pageable pageable
    ) {
        return quizResultInterface.getResults(name,pageable);
    }

    @GetMapping("/top")
    public List<QuizResultResponseDTO> getTopResults() {
        return quizResultInterface.getTopResults();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quizResultInterface.delete(id);
        return ResponseEntity.noContent().build();
    }


}
