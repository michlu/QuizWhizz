package com.pw.quizwhizz.model.dto;

import lombok.Data;

/**
 * @author michlu
 * @sience 02.06.2017
 */
@Data
public class Statistics {
    int numberGames;
    int numberMPGames;
    String mostPopularCategory;
    int mostPopularScore;
}
