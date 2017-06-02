package com.pw.quizwhizz.service;

import com.pw.quizwhizz.model.dto.Statistics;

/**
 * @author michlu
 * @sience 02.06.2017
 */
public interface StatService {

    int findNumberOfUsers();
    Statistics findStatistic();
}
