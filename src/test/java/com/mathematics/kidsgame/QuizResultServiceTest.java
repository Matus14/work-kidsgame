package com.mathematics.kidsgame;

import com.mathematics.kidsgame.DTO.QuizResultRequestDTO;
import com.mathematics.kidsgame.DTO.QuizResultResponseDTO;
import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.repository.QuizResultRepository;
import com.mathematics.kidsgame.service.QuizResultService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizResultServiceTest {

    @Mock
    private QuizResultRepository repository;

    @InjectMocks
    private QuizResultService service;

    @Captor
    private ArgumentCaptor<QuizResult> quizCaptor;


                                        // ====== CREATE =======


    @Test
    void create_whenEntityIsStoredWithCorrectValues_thenCreateDto(){

        QuizResultRequestDTO request = new QuizResultRequestDTO();
        request.setPlayerName("Peter");
        request.setCorrectAnswer(6);
        request.setIncorrectAnswer(4);
        request.setDurationSeconds(180);
        request.setScore(50);



        when(repository.save(any(QuizResult.class)))
                .thenAnswer(inv -> {
                    QuizResult q = inv.getArgument(0);
                    q.setId(4L);
                    return q;
                });

        QuizResultResponseDTO dto = service.saveResult(request);

        verify(repository).save(quizCaptor.capture());
        QuizResult saved = quizCaptor.getValue();

        assertThat(saved.getPlayerName()).isEqualTo("Peter");
        assertThat(saved.getCorrectAnswer()).isEqualTo(6);
        assertThat(saved.getIncorrectAnswer()).isEqualTo(4);
        assertThat(saved.getDurationSeconds()).isEqualTo(180);
        assertThat(saved.getScore()).isEqualTo(50);
        assertThat(saved.getPlayedAt()).isNotNull();

        assertThat(dto.getId()).isEqualTo(4L);
        assertThat(dto.getPlayerName()).isEqualTo("Peter");
        assertThat(dto.getCorrectAnswer()).isEqualTo(6);
        assertThat(dto.getIncorrectAnswer()).isEqualTo(4);
        assertThat(dto.getDurationSeconds()).isEqualTo(180);
        assertThat(dto.getScore()).isEqualTo(50);
        assertThat(dto.getPlayedAt()).isNotNull();


        verifyNoMoreInteractions(repository);
    }

    @Test
    void create_whenNameIsEmpty_thenThrowBadRequest(){

        QuizResultRequestDTO request = new QuizResultRequestDTO();
        request.setPlayerName("  ");
        request.setCorrectAnswer(6);
        request.setIncorrectAnswer(4);
        request.setDurationSeconds(180);
        request.setScore(50);

        assertThatThrownBy(() -> service.saveResult(request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
                )
                .hasMessageContaining("Player name must be filled in");

        verifyNoInteractions(repository);
    }

    @Test
    void create_whenNameIsNull_thenThrowBadRequest(){

        QuizResultRequestDTO request = new QuizResultRequestDTO();
        request.setPlayerName(null);
        request.setCorrectAnswer(6);
        request.setIncorrectAnswer(4);
        request.setDurationSeconds(180);
        request.setScore(50);

        assertThatThrownBy(() -> service.saveResult(request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
                )
                .hasMessageContaining("Player name must be filled in");

        verifyNoInteractions(repository);
    }

    @Test
    void create_whenPlayedAtisMatching_thenSave(){

        QuizResultRequestDTO request = new QuizResultRequestDTO();
        request.setPlayerName("Matus");
        request.setCorrectAnswer(5);
        request.setIncorrectAnswer(6);
        request.setDurationSeconds(120);
        request.setScore(40);

        LocalDateTime before = LocalDateTime.now();

        when(repository.save(any(QuizResult.class)))
                .thenAnswer(inv -> {
                    QuizResult q = inv.getArgument(0);
                    q.setId(1L);
                    return q;
                });

        QuizResultResponseDTO dto = service.saveResult(request);

        LocalDateTime after = LocalDateTime.now();

        verify(repository).save(quizCaptor.capture());
        QuizResult saved = quizCaptor.getValue();

        assertThat(saved.getPlayedAt())
                .isAfterOrEqualTo(before)
                .isAfterOrEqualTo(after);

        assertThat(dto.getPlayedAt())
                .isAfterOrEqualTo(before)
                .isAfterOrEqualTo(after);

        verifyNoMoreInteractions(repository);

    }

}
