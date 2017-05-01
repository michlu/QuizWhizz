package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.GameDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    CategoryService categoryService;
    GameDTOService gameService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Autowired
    public void setGameService(GameDTOService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("games", gameService.findAll());
        return "index";
    }

    @GetMapping("/game")
    public String play() {
        return "ongoing_game";
    }
}
