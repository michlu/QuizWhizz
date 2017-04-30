package com.pw.quizwhizz.model.entity;

import com.pw.quizwhizz.model.GameState;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "game")
public class GameDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Game_id niepotrzebne: gra dostanie id z GameDTO po zapisaniu do bazy

    @OneToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Enumerated
    @Column(name = "current_state")
    GameState currentState;     // enum; w odopwiedniej metodzie z kontrolera uzyskamy go z GameStateMachine

    // Domyslnie mapuje na DATETIME !! sprawdzic czy to odpowiedni format
    @Column(name = "start_time")
    Instant startTime;          // podobnie: po startcie wycigniemy z GSM
                                // Pytania nalezace do konkretnej gry w osobnej klasie i encji: QuestionInGameDTO

}
