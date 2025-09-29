package com.mathematics.kidsgame.service;


import com.mathematics.kidsgame.DTO.QuizResultRequestDTO;
import com.mathematics.kidsgame.DTO.QuizResultResponseDTO;
import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizResultService implements QuizResultInterface {

    @Autowired
    private QuizResultRepository repository;

    @Override
    public QuizResultResponseDTO saveResult(QuizResultRequestDTO request) {

        if(request.getPlayerName() == null || request.getPlayerName().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player name must be filled in");
        }

        QuizResult entity = QuizResult.builder()
                .playerName(request.getPlayerName().trim())
                .correctAnswer(request.getCorrectAnswer())
                .incorrectAnswer(request.getIncorrectAnswer())
                .durationSeconds(request.getDurationSeconds())
                .score(request.getScore())
                .playedAt(LocalDateTime.now())
                .build();

        QuizResult saved = repository.save(entity);
        return toDto(saved);
    }

    @Override
    public List<QuizResultResponseDTO> getAllResults() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "score"))
                .stream()
                .map(this::toDto)
                .toList();

    }

    @Override
    public List<QuizResultResponseDTO> getTopResults(){
        return repository.findTop10ByOrderByScoreDesc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public QuizResultResponseDTO findById(Long id){

            QuizResult result = repository.findById(id)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
            return toDto(result);
    }

    @Override
    public void  delete(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found for delete");
        }
        repository.deleteById(id);

    }

    // Grab results from DB with optional name filter.
    // If "name" is given, do a case-insensitive search by playerName.
    // Otherwise just return all results, but still paged.
    @Override
    public Page<QuizResultResponseDTO> getResults(String name, Pageable pageable) {
        Page<QuizResult> page = (name != null && !name.isBlank())
                ? repository.findByPlayerNameContainingIgnoreCase(name.trim(), pageable)
                : repository.findAll(pageable);
        return page.map(this::toDto);
    }

    private QuizResultResponseDTO toDto(QuizResult q) {
        return new QuizResultResponseDTO(
                q.getId(),
                q.getPlayerName(),
                q.getCorrectAnswer(),
                q.getIncorrectAnswer(),
                q.getDurationSeconds(),
                q.getScore(),
                q.getPlayedAt()
        );
    }
}
