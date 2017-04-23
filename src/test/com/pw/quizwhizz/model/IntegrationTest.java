package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.entity.Answer;
import com.pw.quizwhizz.model.entity.Category;
import com.pw.quizwhizz.model.entity.Question;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.impl.QuestionServiceImpl;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Karolina on 14.04.2017.
 */
public class IntegrationTest {

    @Test
    public void GivenSeveralPlayers_WhenEveryoneSubmitsTheirAnswers_ThenWinnerIsDeterminedAndGivenBonus() throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException, ScoreCannotBeRetrievedBeforeGameIsClosedException {
        // First API call (Get categories)

        // Second API call (Create a game)
        Category testCategory = new Category("Test");
        QuestionService service =  mock(QuestionServiceImpl.class); // should get questions for a given category
        GameStateMachine gsm = mock(GameStateMachine.class);

        List<Question> questions = mock(List.class);
        when(questions.size()).thenReturn(10);
        when(service.get10RandomQuestions(testCategory)).thenReturn(questions);

        Game game = new Game(testCategory, questions, gsm);

        Player player1 = new Player("Player 1");
        PlayerInGame playerOne = new PlayerInGame(player1, game);
        // Third API call (Player joins)
        Player player2 = new Player("Player 2");
        PlayerInGame playerTwo = new PlayerInGame(player2, game);
        // Fourth API call (Player joins)
        Player player3 = new Player("Player 3");
        PlayerInGame playerThree = new PlayerInGame(player3, game);

        // Fifth API call (Owner starts the game)
        playerOne.startGame();

        // Sixth API call (Answers submission)
        playerOne.submitAnswers(getCorrectAnswers(5));
        // Seventh API call
        playerTwo.submitAnswers(getCorrectAnswers(6));
        // Eighth API call
        playerThree.submitAnswers(getCorrectAnswers(3));

        when(gsm.gameIsClosed()).thenReturn(true);
        // Ninth, Tenth, Eleventh API call (Each player wants to see the scores)
        List<Score> scores = game.getScores();

        Player actualWinner = scores.stream()
                .sorted(Comparator.comparingInt(Score::getPoints).reversed())
                .findFirst()
                .get()
                .getPlayer();

        assertThat(playerOne.isOwner()).isTrue();
        assertThat(playerTwo.isOwner()).isFalse();
        assertThat(playerThree.isOwner()).isFalse();
        assertThat(actualWinner).isEqualTo(playerTwo.getPlayer());
        assertThat(actualWinner.getXp()).isEqualTo(6 * 10 + 30); // 6 correct answers and a bonus
    }

    private ArrayList<Answer> getCorrectAnswers(int numberOfCorrectAnswers) {
        ArrayList<Answer> answers = new ArrayList<>();

        for (int i = 0; i < numberOfCorrectAnswers; i++) {
            answers.add(new Answer("", true));
        }

        return answers;
    }
}
