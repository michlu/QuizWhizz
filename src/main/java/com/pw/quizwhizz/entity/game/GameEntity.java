package com.pw.quizwhizz.entity.game;

import com.pw.quizwhizz.model.game.GameState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

/**
 * Encja Game
 * @author Karolina Prusaczyk
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "game")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    @Enumerated
    @Column(name = "current_state")
    GameState currentState;

    @Column(name = "start_time")
    Instant startTime;

    public GameEntity(GameEntityBuilder builder) {
        this.category = builder.getCategory();
        this.currentState = builder.getGameState();
        this.startTime = builder.getStartTime();
    }
}
