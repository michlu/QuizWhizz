package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.game.Answer;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// TODO: handle all application wide exceptions gracefully

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping (value = "/open/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        List<Question> questions = questionService.getQuestionsForNewGame(Long.parseLong(categoryId));
        Game game = gameService.createGame(questions);
        User user = userService.findByEmail(authentication.getName());
        gameService.addOwnerToGame(game, user);

        model.addAttribute("game", game);
        model.addAttribute("questions", questions);

        return "start_game";
    }

    @RequestMapping(value = "/{categoryId}/start/{gameId}")
    public String startGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);
        gameService.startGame(game, user);

        //TODO: If game == null -> start_game

        model.addAttribute("game", game);
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("questions", game.getQuestions());

        return "ongoing_game";
    }

    @RequestMapping(value = "/{gameId}/submitAnswers/{answers}")
    public String submitAnswers(@PathVariable Long gameId, @PathVariable String answers, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);

        for (String answerId : answers.split(",")) {
            System.out.println(answerId);
        }


        return "game_open";
    }

    /* TODO:
    - player: join
    - player: submit answers -->
        -> game -> score: evaluate answers (protected)
    - game: getScores
    - player: check incrementGamesPlayed() + addXp()
     */
}
