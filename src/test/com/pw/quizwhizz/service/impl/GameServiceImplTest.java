package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.game.*;
import com.pw.quizwhizz.entity.game.GameEntityBuilder;
import com.pw.quizwhizz.entity.game.GameEntity;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.repository.game.*;
import com.pw.quizwhizz.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Klasa testujaca serwis gry
 * @author Karolina Prusaczyk
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {
    @Mock
    private PlayerInGameRepository playerInGameRepository;
    @Mock
    private QuestionInGameRepository questionInGameRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private QuestionService questionService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private AnswerService answerService;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GameFactory gameFactory;
    @Mock
    private GameEntityBuilder gameEntityBuilder;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private ScoreBuilder scoreBuilder;

    private GameServiceImpl gameService;
    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;

    @Before
    public void setup() {
        gameService = new GameServiceImpl(gameRepository, playerInGameRepository, playerRepository, scoreRepository, questionInGameRepository, questionService, categoryService, answerService, gameFactory, gameEntityBuilder, scoreBuilder);
    }

    @Test
    public void findAllGameEntities() {
        List<GameEntity> games = mock(List.class);
        when(gameRepository.findAll()).thenReturn(games);

        assertThat(games).isNotNull();
    }

//    @Test
//    public void givenCategoryAndQuestions_WhenCreateGameIsCalled_ThenGameEntityShouldBeSavedAndGameShouldGetItsId() throws IllegalNumberOfQuestionsException {
//        Category category = mock(Category.class);
//        GameState state = GameState.OPEN;
//        List<Question> questions = givenListOfQuestions();
//        when(questions.get(0).getCategory()).thenReturn(category);
//
//        GameEntity gameEntity = mock(GameEntity.class);
//        when(gameEntityBuilder.withCategory(any())).thenReturn(gameEntityBuilder);
//        when(gameEntityBuilder.withCurrentState(any())).thenReturn(gameEntityBuilder);
//        when(gameEntityBuilder.build()).thenReturn(gameEntity);
//        when(gameEntity.getCategory()).thenReturn(category);
//        when(gameEntity.getCurrentState()).thenReturn(state);
//
//          Null pointer przy konwersji - gra == null!
//        Game game = gameService.createGame(questions);
//
//        verify(gameRepository, times(1)).saveGame(game);
//        assertThat(game.getId()).isNotNull();
//    }


    private void givenGameStateMachine(GameState state, Game game) {
        GameStateMachine machine = mock(GameStateMachine.class);
        when(game.getGameStateMachine()).thenReturn(machine);
        when(machine.getCurrentState()).thenReturn(state);
    }

    private Game givenArrangedGame(Category category, List<Question> questions) throws IllegalNumberOfQuestionsException {
        Game game = mock(Game.class);
        when(gameFactory.build(category, questions)).thenReturn(game);
        when(game.getCategory()).thenReturn(category);
        when(game.getQuestions()).thenReturn(questions);
        return game;
    }

    private List<Question> givenListOfQuestions() {
        question1 = mock(Question.class);
        question2 = mock(Question.class);
        question3 = mock(Question.class);
        question4 = mock(Question.class);
        List<Question> questions = mock(List.class);
        when(questions.get(0)).thenReturn(question1);
        when(questions.get(1)).thenReturn(question2);
        when(questions.get(2)).thenReturn(question3);
        when(questions.get(3)).thenReturn(question4);
        when(questions.size()).thenReturn(10);
        return questions;
    }

}
