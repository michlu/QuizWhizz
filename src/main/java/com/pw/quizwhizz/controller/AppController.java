package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }

    @GetMapping("/game")
    public String play() {
        return "ongoing_game";
    }
}
