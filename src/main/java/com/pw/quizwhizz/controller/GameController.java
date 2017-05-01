package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.category.Category;
import com.pw.quizwhizz.model.player.PlayerInGameDTO;
import com.pw.quizwhizz.model.question.Question;
import com.pw.quizwhizz.model.question.QuestionInGameDTO;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameDTOService gameService;
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

    @GetMapping("/start/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        Category category = categoryService.findById(Long.parseLong(categoryId));
        List<Question> questions = questionService.get10RandomQuestions(category);

        Game game = gameService.createGameWithId(category, questions);
        List<QuestionInGameDTO> questionsInGame = questionInGameService.convertToQuestionsInGame(questions, game.getId());

        User currentUser = userService.findByEmail(authentication.getName());
        Player currentPlayer = userService.convertToPlayer(currentUser);
        PlayerInGame player = playerInGameService.convertToPlayerInGame(currentPlayer, game);
        PlayerInGameDTO playerInGameDTO = playerInGameService.convertToPlayerInGameDTO(player);

        playerInGameService.savePlayerInGameDTO(playerInGameDTO);
        questionInGameService.saveQuestionsInGame(questionsInGame);

        model.addAttribute ("category", category);
        model.addAttribute("game", game);
        model.addAttribute("player", player);
        model.addAttribute("questions", questions);

        return "redirect:/game/game_open";
    }

}
