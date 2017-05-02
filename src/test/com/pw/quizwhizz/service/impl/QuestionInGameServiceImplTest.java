package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import com.pw.quizwhizz.repository.QuestionInGameRepository;
import com.pw.quizwhizz.service.QuestionInGameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


///**
// * Created by karol on 30.04.2017.
// */
//@RunWith(MockitoJUnitRunner.class)
//public class QuestionInGameServiceImplTest {
//    @Mock
//    private QuestionInGameRepository repository;
//    private QuestionInGameService service;
//    private List<QuestionInGameDTO> questionsInGame;
//    private Question question1;
//    private Question question2;
//    private Question question3;
//    private Question question4;
//
//    @Before
//    public void setup() {
//        service = new QuestionInGameServiceImpl(repository);
//    }
//
//    @Test
//    public void givenQuestionsInGame_WhenSaveIsCalled_ThenTheyAreSavedInRepository() throws Exception {
//        List<Question> questions = givenListOfQuestions();
//        service.saveQuestionsInGame(questions, 1L);
//
//        for(QuestionInGameDTO question : questionsInGame) {
//            verify(repository, times(1)).save(question);
//        }
//    }
//
//    private List<Question> givenListOfQuestions() {
//        question1 = mock(Question.class);
//        question2 = mock(Question.class);
//        question3 = mock(Question.class);
//        question4 = mock(Question.class);
//        List<Question> questions = mock(List.class);
//        when(questions.get(0)).thenReturn(question1);
//        when(questions.get(1)).thenReturn(question2);
//        when(questions.get(2)).thenReturn(question3);
//        when(questions.get(3)).thenReturn(question4);
//        when(questions.size()).thenReturn(10);
//        return questions;
//    }
//}/**
// * Created by karol on 30.04.2017.
// */
//@RunWith(MockitoJUnitRunner.class)
//public class QuestionInGameServiceImplTest {
//    @Mock
//    private QuestionInGameRepository repository;
//    private QuestionInGameService service;
//    private List<QuestionInGameDTO> questionsInGame;
//    private Question question1;
//    private Question question2;
//    private Question question3;
//    private Question question4;
//
//    @Before
//    public void setup() {
//        service = new QuestionInGameServiceImpl(repository);
//    }
//
//    @Test
//    public void givenQuestionsInGame_WhenSaveIsCalled_ThenTheyAreSavedInRepository() throws Exception {
//        List<Question> questions = givenListOfQuestions();
//        service.saveQuestionsInGame(questions, 1L);
//
//        for(QuestionInGameDTO question : questionsInGame) {
//            verify(repository, times(1)).save(question);
//        }
//    }
//
//    private List<Question> givenListOfQuestions() {
//        question1 = mock(Question.class);
//        question2 = mock(Question.class);
//        question3 = mock(Question.class);
//        question4 = mock(Question.class);
//        List<Question> questions = mock(List.class);
//        when(questions.get(0)).thenReturn(question1);
//        when(questions.get(1)).thenReturn(question2);
//        when(questions.get(2)).thenReturn(question3);
//        when(questions.get(3)).thenReturn(question4);
//        when(questions.size()).thenReturn(10);
//        return questions;
//    }
//}