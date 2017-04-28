package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.entity.Question;
import com.pw.quizwhizz.model.entity.QuestionInGameDTO;
import com.pw.quizwhizz.service.GameService;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {

    private GameService gameService = new GameServiceImpl();
    private List<QuestionInGameDTO> questionsInGame;


    @Test
    public void givenListOfQuestions_WhenConvertIsCalled_ThenQuestionsAreConvertedWithAppropriateSequence() {
        long gameId = 1;
        Question question1 = mock(Question.class);
        Question question2 = mock(Question.class);
        Question question3 = mock(Question.class);
        Question question4 = mock(Question.class);
        List<Question> questions = givenListOfQuestions(question1, question2, question3, question4);

        questionsInGame = gameService.convertToQuestionsInGame(questions, gameId);

        assertThat(questionsInGame.size()).isEqualTo(questions.size());
        assertThat(questionsInGame.get(0).getQuestion()).isEqualTo(question1);
        assertThat(questionsInGame.get(1).getQuestion()).isEqualTo(question2);
        assertThat(questionsInGame.get(2).getQuestion()).isEqualTo(question3);
        assertThat(questionsInGame.get(3).getQuestion()).isEqualTo(question4);

    }

    private List<Question> givenListOfQuestions(Question question1, Question question2, Question question3, Question question4) {
        List<Question> questions = mock(List.class);
        when(questions.get(0)).thenReturn(question1);
        when(questions.get(1)).thenReturn(question2);
        when(questions.get(2)).thenReturn(question3);
        when(questions.get(3)).thenReturn(question4);
        when(questions.size()).thenReturn(10);
        return questions;
    }

}