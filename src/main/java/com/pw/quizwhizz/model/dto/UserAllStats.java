package com.pw.quizwhizz.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author michlu
 * @sience 30.05.2017
 */
@Data
public class UserAllStats implements Serializable {
    int gameId;
    Date gameDate;
    String gameCategory;
    int numberOfPlayers;
    int points;
    String winner;
}
