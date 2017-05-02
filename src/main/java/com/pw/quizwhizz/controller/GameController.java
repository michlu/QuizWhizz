package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.GameDTO;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
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
    private GameDTOService gameDTOService;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PlayerInGameService playerInGameService;
    @Autowired
    private QuestionInGameService questionInGameService;

    // TODO: Handle IllegalNumberOfQuestionsException

    @RequestMapping ("/start/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {

        List<Question> questions = questionService.getQuestionsForNewGame(Long.parseLong(categoryId));
        Game game = gameService.createGame(questions);

        List<QuestionInGameDTO> questionsInGame = questionInGameService.convertToQuestionsInGame(questions, game.getId());

        User currentUser = userService.findByEmail(authentication.getName());
        Player currentPlayer = userService.convertToPlayer(currentUser);
        PlayerInGame player = playerInGameService.convertToPlayerInGame(currentPlayer, game);
        PlayerInGameDTO playerInGameDTO = playerInGameService.convertToPlayerInGameDTO(player);

        playerInGameService.savePlayerInGameDTO(playerInGameDTO);
        questionInGameService.saveQuestionsInGame(questionsInGame);

        model.addAttribute("game", game);
        model.addAttribute("gameId", game.getId());
        model.addAttribute("player", player);
        model.addAttribute("questions", questions);

        return "/start_game";

         // return "redirect:/game/play/" + game.getId();
    }

    @RequestMapping("/play/{gameId}")
    public String startGame(@PathVariable String gameId, Model model) throws IllegalNumberOfQuestionsException {
        long gameID = Long.parseLong(gameId);



        // TODO: Uwaga, kazde klikniecie Start podbija id gry
        // TODO: z ktorego teraz korzystac?
        GameDTO gameDTO = gameDTOService.findById(gameID);
        Game game = gameDTOService.findGameById(gameID);



        model.addAttribute("gameId", Long.parseLong(gameId));
        model.addAttribute("questions", questionInGameService.findQuestionsInGameByGameId(Long.parseLong(gameId)));

        return "/ongoing_game";
    }
}
