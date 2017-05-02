package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.GameDTO;
import com.pw.quizwhizz.model.game.GameDTOBuilder;
import com.pw.quizwhizz.model.game.GameFactory;
import com.pw.quizwhizz.model.game.GameState;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import com.pw.quizwhizz.repository.GameRepository;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.QuestionInGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by karol on 02.05.2017.
 */
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameFactory gameFactory;
    private final GameDTOBuilder gameDTOBuilder;

    private final QuestionInGameService questionInGameService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameFactory gameFactory, GameDTOBuilder gameDTOBuilder, QuestionInGameService questionInGameService) {
        this.gameRepository = gameRepository;
        this.gameFactory = gameFactory;
        this.gameDTOBuilder = gameDTOBuilder;
        this.questionInGameService = questionInGameService;
    }

    @Override
    public Game createGame(List<Question> questions) throws IllegalNumberOfQuestionsException {
        Category category = questions.get(0).getCategory();
        Game game = gameFactory.build(category, questions);
        GameDTO gameDTO = convertToGameDTO(game);
        gameRepository.save(gameDTO);
        game.setId(gameDTO.getId());
        questionInGameService.saveQuestionsInGame(questions, game.getId());
        return game;
    }

    private GameDTO convertToGameDTO(Game game) {
        Category category = game.getCategory();
        GameState currentState = game.getGameStateMachine().getCurrentState();
        GameDTO gameDTO = gameDTOBuilder.withCategory(category)
                .withCurrentState(currentState)
                .build();
        return gameDTO;
    }
}
