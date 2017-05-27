package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    private final CategoryService categoryService;
    private final GameService gameService;

    @Autowired
    public AppController(CategoryService categoryService, GameService gameService) {
        this.categoryService = categoryService;
        this.gameService = gameService;
    }

    @RequestMapping("/")
    public String home(Model model) throws IllegalNumberOfQuestionsException {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("games", gameService.getAllOpenGames());
        return "index";
    }

    // do testow.. wynik gry
    @RequestMapping("/end")
    public String endgame() throws IllegalNumberOfQuestionsException {
        return "check_scores";
    }
}
