package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.entity.game.CategoryEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Klasa testująca poprawnosc losowania pytań do gry z puli danej kategorii
 *
 * @author Karolina Prusaczyk
 * @see com.pw.quizwhizz.service.QuestionService
 */
public class QuestionServiceHardcodedTest {
    private QuestionServiceHardcoded questionService = new QuestionServiceHardcoded();
    private Category category = new Category();

    /**
     * Test sprawdzajacy, ze dwa losowania pytan zwroca dwa rozne ich zestawy.
     */
    @Test
    public void Given2ListsOfQuestions_WhenCheckedForEquality_ThenFalseShouldBeReturned() {

        List<Question> q1 = questionService.getRandomQuestionsByCategory(category, 10);
        List<Question> q2 = questionService.getRandomQuestionsByCategory(category, 10);

        Assertions.assertThat(q1).isNotEqualTo(q2);
    }

    /**
     * Test sprawdzajacy, ze kazde losowanie zwraca zestaw o pozadanej liczbie pytan.
     */
    @Test
    public void Given3ListOfQuestions_WhenCheckedForSize_ThenAllShouldContain10Questions() {
        List<Question> q1 = questionService.getRandomQuestionsByCategory(category, 10);
        List<Question> q2 = questionService.getRandomQuestionsByCategory(category, 10);
        List<Question> q3 = questionService.getRandomQuestionsByCategory(category, 10);

        int size1 = q1.size();
        int size2 = q2.size();
        int size3 = q3.size();

        assertThat(size1 == size2  && size2 == size3 && size3 == 10);
    }

    /**
     * Test potwierdzajacy, ze pytania w losowanych zestawach nie duplikuja się.
     */
    @Test
    public void GivenList_WhenCheckedForUniquenessOfQuestions_ThenTrueShouldBeReturned() {
        List<Question> q1 = questionService.getRandomQuestionsByCategory(category, 10);

        Assertions.assertThat(q1).doesNotHaveDuplicates();

    }

}