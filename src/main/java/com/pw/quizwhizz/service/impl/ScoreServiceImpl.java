package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.repository.game.ScoreRepository;
import com.pw.quizwhizz.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService{
    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }
}
