package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.dto.Ranking;
import com.pw.quizwhizz.model.dto.Statistics;

import java.util.List;

/**
 * @author michlu
 * @sience 02.06.2017
 */
public interface StatService {
    int findNumberOfUsers();
    Statistics findStatistic();
    List<Ranking> findGeneralRank(int limitSearch);
    List<Ranking> findFiveByCategory(int limitSearch , Long categoryId);
}
