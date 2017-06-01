package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    private final CategoryService categoryService;
    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public AppController(CategoryService categoryService, GameService gameService, UserService userService) {
        this.categoryService = categoryService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home(Model model) throws IllegalNumberOfQuestionsException {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("games", gameService.getAllOpenGames());
        model.addAttribute("generalRank", userService.findGeneralRank(3)); // ustaw ilosc graczy w rankingu generalnym
        return "index";
    }

    // do testow.. wynik gry
    @RequestMapping("/end")
    public String endgame() throws IllegalNumberOfQuestionsException {
        return "check_scores";
    }
}
