package com.pw.quizwhizz.model.dto;

import lombok.Data;

/**
 * @author michlu
 * @sience 01.06.2017
 */
@Data
public class Ranking {
    String playerImageUrl;
    String playerName;
    int gamePlayed;
//    int winMultiplayer; // ilosc wygranych w grze wieloosobowej
    int allPoints;
    Long userID;
}
