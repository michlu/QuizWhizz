package com.pw.quizwhizz.model.game;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameDTO {
    private long gameId;
    private String categoryName;
    private String ownerName;
    private String displayStateName;
}
