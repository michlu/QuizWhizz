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
    private int gameId;
    private Date gameDate;
    private String gameCategory;
    private int numberOfPlayers;
    private int points;
    private String winner;
}
