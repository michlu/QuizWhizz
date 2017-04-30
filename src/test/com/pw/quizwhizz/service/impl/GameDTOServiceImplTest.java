package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.game.GameFactory;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.game.GameDTO;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.repository.GameDTORepository;
import com.pw.quizwhizz.repository.GameStatsRepository;
import com.pw.quizwhizz.repository.PlayerInGameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceDTOImplTest {
    @Mock
    private PlayerInGameRepository playerInGameRepository;
    @Mock
    private GameDTORepository gameDTORepository;
    @Mock
    private GameStatsRepository gameStatsRepository;
    @Mock
    private GameFactory gameFactory;
    private GameDTOServiceImpl gameService;

    @Before
    public void setup() {
    gameService = new GameDTOServiceImpl(gameDTORepository, playerInGameRepository, gameStatsRepository, gameFactory);
    }

    private List<QuestionInGameDTO> questionsInGame;
    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;


    @Test
    public void findAllGameDTOS() {
        List<GameDTO> games = mock(List.class);
        when(gameDTORepository.findAll()).thenReturn(games);

        assertThat(games).isNotNull();
    }

    @Test
    public void givenCategoryAndQuestions_WhenCreateGameIsCalled_ThenGameDTOShouldBeSavedAndStatusShouldBeOpen() throws IllegalNumberOfQuestionsException {
        Category category = mock(Category.class);
        List<Question> questions = givenListOfQuestions();

//        GameDTO gameDTO = mock(GameDTO.class);
//        when(gameDTO.getId()).thenReturn(10L);

        Game game = mock(Game.class);
        when(game.getCategory()).thenReturn(category);
        when(game.getQuestions()).thenReturn(questions);
        when(gameService.createGameWithId(category, questions)).thenReturn(game);

        assertThat(game).isNotNull();
        assertThat(game.getId()).isNotNull();
      //  verify(gameDTORepository, times(1)).save(gameService.saveAsGameDTO(game));
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
