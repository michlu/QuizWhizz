package com.pw.quizwhizz.model.dto;

import lombok.Data;

/**
 * Klasa DTO transportujaca dane przekazywane do list rankingu.
 * @author Michał Nowiński
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
