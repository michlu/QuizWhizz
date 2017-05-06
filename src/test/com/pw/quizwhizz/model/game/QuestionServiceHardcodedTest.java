package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.dto.game.CategoryDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Karolina on 26.03.2017.
 */
public class QuestionServiceHardcodedTest {
    private QuestionServiceHardcoded questionService = new QuestionServiceHardcoded();
    private Category category = new Category();

    @Test
    public void Given2ListsOfQuestions_WhenCheckedForEquality_ThenFalseShouldBeReturned() {

        List<Question> q1 = questionService.getRandomQuestionsByCategory(category, 10);
        List<Question> q2 = questionService.getRandomQuestionsByCategory(category, 10);

        Assertions.assertThat(q1).isNotEqualTo(q2);
    }

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

    @Test
    public void GivenList_WhenCheckedForUniquenessOfQuestions_ThenTrueShouldBeReturned() {
        List<Question> q1 = questionService.getRandomQuestionsByCategory(category, 10);

        Assertions.assertThat(q1).doesNotHaveDuplicates();

    }

}