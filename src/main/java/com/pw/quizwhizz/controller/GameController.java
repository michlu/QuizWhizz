package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.entity.Category;
import com.pw.quizwhizz.model.entity.Question;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    private GameService gameService;
    private UserService userService;
    private CategoryService categoryService;
    private QuestionService questionService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/start/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        Category category = categoryService.findById(Long.parseLong(categoryId));
        List<Question> questions = questionService.get10RandomQuestions(category);
        Game game = gameService.createGame(category, questions);





        //  List<QuestionInGameDTO> questionsInGame = gameService.convertToQuestionsInGame(questions, gameId);



        model.addAttribute("category", category);
        model.addAttribute("user", userService.findByEmail(authentication.getName()));
        /*
            Przygotowanie gry
         */
//        model.addAttribute("game",
//                gameService.addGame(
//                        new Game(category,
//                                questionList,
//                                gsm
//                        )));

        return "redirect:/game/game_open";
    }

}
