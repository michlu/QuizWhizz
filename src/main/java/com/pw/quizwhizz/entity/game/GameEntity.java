package com.pw.quizwhizz.entity.game;

import com.pw.quizwhizz.model.game.GameState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name = "game")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Game_id niepotrzebne: gra dostanie id z GameEntity po zapisaniu do bazy
    @OneToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    @Enumerated
    @Column(name = "current_state")
    GameState currentState;     // enum; w odopwiedniej metodzie z kontrolera uzyskamy go z GameStateMachine

    // Domyslnie mapuje na DATETIME !! sprawdzic czy to odpowiedni format
    @Column(name = "start_time")
    Instant startTime;          // podobnie: po startcie wycigniemy z GSM

    public GameEntity(GameEntityBuilder builder) {
        this.category = builder.getCategory();
        this.currentState = builder.getGameState();
        this.startTime = builder.getStartTime();
    }
    // Pytania nalezace do konkretnej gry w osobnej klasie i encji: QuestionInGameEntity

}
