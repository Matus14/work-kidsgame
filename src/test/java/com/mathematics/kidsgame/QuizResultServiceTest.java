package com.mathematics.kidsgame;

import com.mathematics.kidsgame.DTO.QuizResultRequestDTO;
import com.mathematics.kidsgame.DTO.QuizResultResponseDTO;
import com.mathematics.kidsgame.entity.QuizResult;
import com.mathematics.kidsgame.repository.QuizResultRepository;
import com.mathematics.kidsgame.service.QuizResultService;
import net.bytebuddy.TypeCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


                                          // ===== FIND ALL =====


    @Test
    void findAll_whenStoredEntitiesFound_thenSendOutAllOutAsDto(){

        QuizResult q1 = QuizResult.builder()
                .id(4L)
                .playerName("Martin")
                .correctAnswer(5)
                .incorrectAnswer(5)
                .durationSeconds(120)
                .playedAt(LocalDateTime.now().minusSeconds(100))
                .build();

        QuizResult q2 = QuizResult.builder()
                .id(2L)
                .playerName("Peter")
                .correctAnswer(3)
                .incorrectAnswer(7)
                .durationSeconds(110)
                .playedAt(LocalDateTime.now().minusSeconds(110))
                .build();

        when(repository.findAll(any(Sort.class))).thenReturn(List.of(q1,q2));

        List <QuizResultResponseDTO> result = service.getAllResults();

        assertThat(result).hasSize(2);

        assertThat(result.get(0).getId()).isEqualTo(4L);
        assertThat(result.get(0).getPlayerName()).isEqualTo("Martin");
        assertThat(result.get(0).getCorrectAnswer()).isEqualTo(5);
        assertThat(result.get(0).getIncorrectAnswer()).isEqualTo(5);
        assertThat(result.get(0).getDurationSeconds()).isEqualTo(120);
        assertThat(result.get(0).getPlayedAt()).isEqualTo(q1.getPlayedAt());

        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getPlayerName()).isEqualTo("Peter");
        assertThat(result.get(1).getCorrectAnswer()).isEqualTo(3);
        assertThat(result.get(1).getIncorrectAnswer()).isEqualTo(7);
        assertThat(result.get(1).getDurationSeconds()).isEqualTo(110);
        assertThat(result.get(1).getPlayedAt()).isEqualTo(q2.getPlayedAt());

        verify(repository).findAll(any(Sort.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll_whenListEmpty_thenShowEmptyList() {

        when(repository.findAll(any(Sort.class))).thenReturn(List.of());

        List <QuizResultResponseDTO> result = service.getAllResults();

        assertThat(result).isEmpty();

        verify(repository).findAll(any(Sort.class));
        verifyNoMoreInteractions(repository);

    }

                                      // ======= GET TOP RESULTS =======

    @Test
    void getTopResults_whenStoredEntityFoundBasedOnPlace_thenShowDto(){

        QuizResult q1 = QuizResult.builder()
                .id(4L)
                .playerName("Martin")
                .correctAnswer(5)
                .incorrectAnswer(5)
                .durationSeconds(120)
                .playedAt(LocalDateTime.now().minusSeconds(100))
                .score(50)
                .build();

        QuizResult q2 = QuizResult.builder()
                .id(2L)
                .playerName("Peter")
                .correctAnswer(3)
                .incorrectAnswer(7)
                .durationSeconds(110)
                .playedAt(LocalDateTime.now().minusSeconds(110))
                .score(20)
                .build();

        when(repository.findTop10ByOrderByScoreDesc()).thenReturn(List.of(q1,q2));

        List<QuizResultResponseDTO> result = service.getTopResults();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(4L);
        assertThat(result.get(0).getPlayerName()).isEqualTo("Martin");
        assertThat(result.get(0).getCorrectAnswer()).isEqualTo(5);
        assertThat(result.get(0).getIncorrectAnswer()).isEqualTo(5);
        assertThat(result.get(0).getDurationSeconds()).isEqualTo(120);
        assertThat(result.get(0).getPlayedAt()).isEqualTo(q1.getPlayedAt());
        assertThat(result.get(0).getScore()).isEqualTo(50);

        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getPlayerName()).isEqualTo("Peter");
        assertThat(result.get(1).getCorrectAnswer()).isEqualTo(3);
        assertThat(result.get(1).getIncorrectAnswer()).isEqualTo(7);
        assertThat(result.get(1).getDurationSeconds()).isEqualTo(110);
        assertThat(result.get(1).getPlayedAt()).isEqualTo(q2.getPlayedAt());
        assertThat(result.get(1).getScore()).isEqualTo(20);

        assertThat(result.get(0).getScore()).isGreaterThanOrEqualTo(result.get(1).getScore());

        verify(repository).findTop10ByOrderByScoreDesc();
        verifyNoMoreInteractions(repository);
    }


                        // ========= FIND BY ID =========


    @Test
    void findById_whenEntityHasValidId_thenReturnDto(){

        QuizResult quiz = QuizResult.builder()
                .id(4L)
                .playerName("Martin")
                .correctAnswer(5)
                .incorrectAnswer(5)
                .durationSeconds(120)
                .playedAt(LocalDateTime.now().minusSeconds(100))
                .score(50)
                .build();

        when(repository.findById(4L)).thenReturn(Optional.of(quiz));

        QuizResultResponseDTO dto = service.findById(4L);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(4L);
        assertThat(dto.getPlayerName()).isEqualTo("Martin");
        assertThat(dto.getCorrectAnswer()).isEqualTo(5);
        assertThat(dto.getIncorrectAnswer()).isEqualTo(5);
        assertThat(dto.getDurationSeconds()).isEqualTo(120);
        assertThat(dto.getPlayedAt()).isEqualTo(quiz.getPlayedAt());
        assertThat(dto.getScore()).isEqualTo(50);

        verify(repository).findById(4L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findById_whenEntityIdNotFound_thenThrowNotFound() {

        when(repository.findById(4L)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> service.findById(4L))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
                )
                .hasMessageContaining("Id not found");

        verify(repository).findById(4L);
        verifyNoMoreInteractions(repository);

    }

                                 // ======== DELETE =========


    @Test
    void delete_whenIdValidForDelete(){


        when(repository.existsById(4L)).thenReturn(true);

        service.delete(4L);

        verify(repository).existsById(4L);
        verify(repository).deleteById(4L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void delete_InvalidID_thenThrowNotFound(){

        when(repository.existsById(4L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(4L))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
                )
                .hasMessageContaining("Id not found for delete");

        verify(repository).existsById(4L);
        verify(repository, never()).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }

                                        // ======= GET RESULTS (PAGE) ========

    @Test
    void getResults_whenNameProvided_thenCallFindByPlayerNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 2);

        QuizResult q1 = QuizResult.builder()
                .id(1L)
                .playerName("Peter")
                .correctAnswer(5)
                .incorrectAnswer(1)
                .durationSeconds(100)
                .score(80)
                .playedAt(LocalDateTime.now().minusSeconds(60))
                .build();

        Page<QuizResult> page = new PageImpl<>(List.of(q1), pageable, 1);

        when(repository.findByPlayerNameContainingIgnoreCase("Peter", pageable))
                .thenReturn(page);

        Page<QuizResultResponseDTO> result = service.getResults("  Peter  ", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getPlayerName()).isEqualTo("Peter");
        assertThat(result.getTotalElements()).isEqualTo(1);

        verify(repository).findByPlayerNameContainingIgnoreCase("Peter", pageable);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getResults_whenNameIsBlank_thenCallFindAll() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<QuizResult> page = new PageImpl<>(List.of(), pageable, 0);

        when(repository.findAll(pageable)).thenReturn(page);

        Page<QuizResultResponseDTO> result = service.getResults("   ", pageable);

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(0);

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

}
