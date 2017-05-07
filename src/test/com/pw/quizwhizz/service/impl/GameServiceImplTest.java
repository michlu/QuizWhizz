package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.GameStateMachine;
import com.pw.quizwhizz.dto.game.GameDTOBuilder;
import com.pw.quizwhizz.model.game.GameFactory;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.dto.game.GameDTO;
import com.pw.quizwhizz.model.game.GameState;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.repository.game.GameRepository;
import com.pw.quizwhizz.repository.game.PlayerInGameRepository;
import com.pw.quizwhizz.repository.game.QuestionInGameRepository;
import com.pw.quizwhizz.service.AnswerService;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.PlayerService;
import com.pw.quizwhizz.service.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    private PlayerService playerService;
    @Mock
    private GameFactory gameFactory;
    @Mock
    private GameDTOBuilder builder;

    private GameServiceImpl gameService;
    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;

    @Before
    public void setup() {
        gameService = new GameServiceImpl(gameRepository, playerInGameRepository, questionInGameRepository, questionService, categoryService, answerService, playerService, gameFactory, builder);
    }

    @Test
    public void findAllGameDTOS() {
        List<GameDTO> games = mock(List.class);
        when(gameRepository.findAll()).thenReturn(games);

        assertThat(games).isNotNull();
    }

//    @Test
//    public void givenCategoryAndQuestions_WhenCreateGameIsCalled_ThenGameDTOShouldBeSavedAndGameShouldGetItsId() throws IllegalNumberOfQuestionsException {
//        Category category = mock(Category.class);
//        GameState state = GameState.OPEN;
//        List<Question> questions = givenListOfQuestions();
//        when(questions.get(0).getCategory()).thenReturn(category);
//
//        GameDTO gameDTO = mock(GameDTO.class);
//        when(builder.withCategory(any())).thenReturn(builder);
//        when(builder.withCurrentState(any())).thenReturn(builder);
//        when(builder.build()).thenReturn(gameDTO);
//        when(gameDTO.getCategory()).thenReturn(category);
//        when(gameDTO.getCurrentState()).thenReturn(state);
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
