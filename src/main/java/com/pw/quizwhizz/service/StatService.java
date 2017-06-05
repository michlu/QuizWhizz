package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.dto.Ranking;
import com.pw.quizwhizz.model.dto.Statistics;

import java.util.List;

/**
 * Abstrakcyjna warstwa serwisu
 * @author Michał Nowiński
 */
public interface StatService {
    int findNumberOfUsers();
    int findNumberOfQuestions();
    Statistics findStatistic();
    List<Ranking> findGeneralRank(int limitSearch);
    List<Ranking> findFiveByCategory(int limitSearch , Long categoryId);
}
