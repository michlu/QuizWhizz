package com.pw.quizwhizz.repository;

import com.pw.quizwhizz.model.Game;
import java.util.HashMap;

public interface GameRepositoryInMemory {

    Game create(Game game);

    Game read(Long gameId);

    void update(Long gameId, Game game);

    void delete(Long gameId);

    HashMap<Long, Game> findAll();
}
