package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Player;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping ("/create/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        List<Question> questions = questionService.getQuestionsForNewGame(Long.parseLong(categoryId));
        Game game = gameService.createGame(questions);
        User user = userService.findByEmail(authentication.getName());
        gameService.addOwnerToGame(game, user);

        model.addAttribute("game", game);
        model.addAttribute("questions", questions);

        return "/start_game";
        // /game/open
    }

    @RequestMapping("/start/{gameId}")
    public String startGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        Game game = gameService.findGameById(gameId);
        User currentUser = userService.findByEmail(authentication.getName());
        //Player player = gameService.findPlayerByUserAndGame(currentUser, game);

//        System.out.println("Players: " + game.getPlayers().size() + " " + game.getPlayers().get(0).getName());
//        System.out.println("PlayerInGame: is owner? " + player.isOwner());
//        System.out.println("State: " + game.getGameStateMachine().getCurrentState());

        model.addAttribute("game", game);
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("questions", game.getQuestions());

        return "/ongoing_game";
        // /game/started
    }
}
