package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.GameStateMachine;
import com.pw.quizwhizz.model.game.GameDTOBuilder;
import com.pw.quizwhizz.model.game.GameFactory;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.game.GameDTO;
import com.pw.quizwhizz.model.game.GameState;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.repository.GameRepository;
import com.pw.quizwhizz.repository.GameStatsRepository;
import com.pw.quizwhizz.repository.PlayerInGameRepository;
import com.pw.quizwhizz.service.QuestionInGameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameDTOServiceImplTest {
    @Mock
    private PlayerInGameRepository playerInGameRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameStatsRepository gameStatsRepository;
    @Mock
    private GameFactory gameFactory;
    @Mock
    private GameDTOBuilder builder;
    @Mock
    private QuestionInGameService questionInGameService;
    private GameDTOServiceImpl gameService;
    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;

    @Before
    public void setup() {
        gameService = new GameDTOServiceImpl(gameRepository, playerInGameRepository, questionInGameService, gameStatsRepository, gameFactory, builder);
    }

    @Test
    public void findAllGameDTOS() {
        List<GameDTO> games = mock(List.class);
        when(gameRepository.findAll()).thenReturn(games);

        assertThat(games).isNotNull();
    }

    @Test
    public void givenCategoryAndQuestions_WhenCreateGameIsCalled_ThenGameDTOShouldBeSavedAndGameShouldGetItsId() throws IllegalNumberOfQuestionsException {
        Category category = mock(Category.class);
        List<Question> questions = givenListOfQuestions();
        GameState state = GameState.OPEN;

        Game game = givenArrangedGame(category, questions);
        givenGameStateMachine(state, game);

        GameDTO gameDTO = givenGameDTO(category, state);
        when(gameService.convertToGameDTO(game)).thenReturn(gameDTO);
        when(gameDTO.getId()).thenReturn(1L);

        gameService.createGameWithId(category, questions);

        verify(gameRepository, times(1)).save(gameDTO);
        verify(game, times(1)).setId(gameDTO.getId());
    }

    private GameDTO givenGameDTO(Category category, GameState state) {
        GameDTO gameDTO = mock(GameDTO.class);
        when(builder.withCategory(any())).thenReturn(builder);
        when(builder.withCurrentState(any())).thenReturn(builder);
        when(builder.build()).thenReturn(gameDTO);
        when(gameDTO.getCategory()).thenReturn(category);
        when(gameDTO.getCurrentState()).thenReturn(state);
        return gameDTO;
    }

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
