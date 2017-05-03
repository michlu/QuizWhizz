package com.pw.quizwhizz.repository.impl;

import com.pw.quizwhizz.model.game.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GameRepositoryInMemory implements com.pw.quizwhizz.repository.GameRepositoryInMemory {
    private Map<Long, Game> listOfGames = new HashMap<>();


    @Override
    public HashMap<Long, Game> findAll() {
        return (HashMap<Long, Game>) listOfGames;
    }

    @Override
    public Game create(Game game) {
        if(listOfGames.keySet().contains(game.getId())) {
            throw new IllegalArgumentException(String.format("Nie można utworzyć gry. Gra o wskazanym id już istnieje.", game.getId()));
        }

        listOfGames.put(game.getId(), game);
        return game;
    }

    @Override
    public Game read(Long gameId) {
        return listOfGames.get(gameId);
    }

    @Override
    public void update(Long gameId, Game game) {
        if(!listOfGames.keySet().contains(gameId)) {
            throw new IllegalArgumentException(String.format("Nie można zaktualizować gry. Gra o wskazanym id nie istnieje.", gameId));
        }

        listOfGames.put(gameId, game);
    }

    @Override
    public void delete(Long gameId) {
        if(!listOfGames.keySet().contains(gameId)) {
            throw new IllegalArgumentException(String.format("Nie można usunąć gry. Gra o wskazanym id nie istnieje.", gameId));
        }

        listOfGames.remove(gameId);
    }


}
