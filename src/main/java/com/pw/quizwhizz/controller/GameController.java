package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QuestionService questionService;


    // TODO: Handle IllegalNumberOfQuestionsException

    @RequestMapping ("/start/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        List<Question> questions = questionService.getQuestionsForNewGame(Long.parseLong(categoryId));
        Game game = gameService.createGame(questions);

        User currentUser = userService.findByEmail(authentication.getName());
        PlayerInGame player = gameService.getNewPlayerInGame(currentUser, game);
        System.out.println("Players: " + game.getPlayers().size() + " " + game.getPlayers().get(0).getName());

        model.addAttribute("game", game);
        model.addAttribute("gameId", game.getId());
        model.addAttribute("player", player);
        model.addAttribute("questions", questions);

        return "/start_game";

         // return "redirect:/game/play/" + game.getId();
    }

    //TODO: Determine where null for user in PlayerDTO comes from

    @RequestMapping("/play/{gameId}")
    public String startGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        Game game = gameService.findGameById(gameId);
        User currentUser = userService.findByEmail(authentication.getName());
        PlayerInGame player = gameService.findPlayerInGameByUserAndGame(currentUser, game);

        System.out.println("Players: " + game.getPlayers().size() + " " + game.getPlayers().get(0).getName());
        System.out.println("PlayerInGame: is owner? " + player.isOwner());
        model.addAttribute("game", game);
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("questions", game.getQuestions());

        return "/ongoing_game";
    }
}
