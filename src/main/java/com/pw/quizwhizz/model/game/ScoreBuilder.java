package com.pw.quizwhizz.model.game;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Getter
@Component
public class ScoreBuilder {
    private Player player;
    private long gameId;
    private int points;
    private boolean isHighest;

    public Score build() {
        return new Score(this);
    }

    public ScoreBuilder withPlayer(Player player){
        this.player = player;
        return this;
    }

    public ScoreBuilder withGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    public ScoreBuilder withPoints(int points) {
        this.points = points;
        return this;
    }

    public ScoreBuilder withIsHighest(boolean isHighest) {
        this.isHighest = isHighest;
        return this;
    }
}
