package com.pw.quizwhizz.model.dto;

import lombok.Data;

/**
 * Klasa DTO przekazujaca dane do statystky portalu
 * @author Michał Nowiński
 */
@Data
public class Statistics {
    int numberGames;
    int numberMPGames;
    String mostPopularCategory;
    int mostPopularScore;
}
