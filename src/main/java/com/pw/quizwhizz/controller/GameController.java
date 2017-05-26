package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.model.game.Score;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.UserService;
import com.pw.quizwhizz.service.exception.NoQuestionsInDBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

// TODO: handle all application wide exceptions gracefully

@Controller
@RequestMapping("/game")
public class GameController {
    final private GameService gameService;
    final private UserService userService;
    final private QuestionService questionService;

    @Autowired
    public GameController(GameService gameService, UserService userService, QuestionService questionService) {
        this.gameService = gameService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @RequestMapping(value = "/open/forCategory/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        List<Question> questions;
        try {
            questions = questionService.getQuestionsForNewGame(Long.parseLong(categoryId));
        } catch (NoQuestionsInDBException e) {
            return "redirect:/";
        }

        Game game = gameService.createGame(questions);
        User user = userService.findByEmail(authentication.getName());
        gameService.addOwnerToGame(game, user);

        fillModelForOpenGamePage(model, game, true);
        return "open_game";
    }

    @RequestMapping(value = "/{gameId}/joinOpened")
    public String joinOpenedGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);

        gameService.addPlayerToGame(game, user);
        boolean isOwner = gameService.isPlayerGameOwner(user.getId(), gameId);

        fillModelForOpenGamePage(model, game, isOwner);
        return "open_game";
    }

    @RequestMapping(value = "/{gameId}/start")
    public String startGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);

        gameService.startGame(game, user);

        fillModelForStartedGamePage(model, game);
        return "started_game";
    }

    @RequestMapping(value = "/{gameId}/getNamesOfPlayers", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getNamesOfPlayers(@PathVariable Long gameId) throws IllegalNumberOfQuestionsException {
        List<String> names = gameService.getNamesOfPlayersInGame(gameId);

        String playersJson = "[";
        for (String name : names) {
            playersJson += "{ \"name\" : \"" + name + "\" }, ";
        }
        playersJson = playersJson.replaceAll(", $", "");
        playersJson += "]";

        return playersJson;
    }

    @RequestMapping(value = "/{gameId}/isStarted", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String isGameStarted(@PathVariable Long gameId) throws IllegalNumberOfQuestionsException {
        boolean isStarted = gameService.isGameStarted(gameId);

        return "{ \"isStarted\" : " + isStarted + " }";
    }

    @RequestMapping(value = "/{gameId}/joinStarted")
    public String joinStartedGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        Game game = gameService.findGameById(gameId);

        fillModelForStartedGamePage(model, game);
        return "started_game";
    }

    @RequestMapping(value = "/{gameId}/submitAnswers")
    public String submitAnswers(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        model.addAttribute("gameId", gameId);
        return "submit_answers";
    }

    @RequestMapping(value = "/{gameId}/submitAnswers/{answers}")
    public String submitAnswers(@PathVariable Long gameId, @PathVariable String answers, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);
        List<Long> answerIds = new ArrayList<>();

        if (answers != null || answers != "") {
            for (String answerId : answers.split(",")) {
                System.out.println(answerId);
                long id = Long.parseLong(answerId);
                answerIds.add(id);
            }
            gameService.submitAnswers(game, user, answerIds);
        }
        model.addAttribute("gameId", gameId);
        return "submit_answers";
    }
    // ze strony submit_answers -> skrypt i przekierowanie na check_scores jesli stan gry = closed

    @RequestMapping(value = "/{gameId}/isClosed", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String isGameClosed(@PathVariable Long gameId) throws IllegalNumberOfQuestionsException {
        boolean isClosed = gameService.isGameClosed(gameId);

        return "{ \"isClosed\" : " + isClosed + " }";
    }

    @RequestMapping(value = "/{gameId}/checkScores")
    public String checkScores(@PathVariable Long gameId, Model model, Authentication authentication) throws ScoreCannotBeRetrievedBeforeGameIsClosedException, IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());

        List<Score> scores = gameService.checkScores(gameId);

        model.addAttribute("scores", scores);
        return "check_scores";
    }


    private void fillModelForOpenGamePage(Model model, Game game, boolean isOwner) {
        model.addAttribute("game", game);
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("isOwner", isOwner);
    }

    private void fillModelForStartedGamePage(Model model, Game game) {
        model.addAttribute("game", game);
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("questions", game.getQuestions());
    }

    /* TODO:
    - game: checkScores - if status==closed - ok
                        - if not closed == wait for all results
     */
}
