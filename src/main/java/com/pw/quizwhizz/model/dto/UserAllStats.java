package com.pw.quizwhizz.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * Klasa DTO transportujaca dane dla statystyk danego uzytkownika.
 * @author Michał Nowiński
 */
@Data
public class UserAllStats {
    private int gameId;
    private Date gameDate;
    private String gameCategory;
    private int numberOfPlayers;
    private int points;
    private String winner;
}
