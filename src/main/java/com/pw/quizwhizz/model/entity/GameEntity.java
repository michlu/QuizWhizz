package com.pw.quizwhizz.model.entity;

import com.pw.quizwhizz.model.GameState;
import lombok.Data;

import java.time.Instant;

@Data
public class GameEntity {
    Long id;
    Category categoryId; // jakies powiazanie z kategoria
    GameState currentState;     // enum; w odopwiedniej metodzie z kontrolera uzyskamy go z GameStateMachine
    Instant startTime;          // podobnie: po startcie wycigniemy z GSM
                                // Pytania nalezace do konkretnej gry w osobnej klasie i encji: QuestionInGame
}
