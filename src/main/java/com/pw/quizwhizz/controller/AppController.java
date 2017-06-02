package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.dto.Ranking;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.StatService;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {
    private final static int GENERAL_RANKING_LIMIT = 5; // ustaw ilosc graczy w rankingu generalnym
    private final static int CATEGORY_RANKING_LIMIT = 3; // ustaw ilosc graczy w rankingu kategorii

    private final CategoryService categoryService;
    private final GameService gameService;
    private final UserService userService;
    private final StatService statService;

    @Autowired
    public AppController(CategoryService categoryService, GameService gameService, UserService userService, StatService statService) {
        this.categoryService = categoryService;
        this.gameService = gameService;
        this.userService = userService;
        this.statService = statService;
    }

    @RequestMapping("/")
    public String home(Model model) throws IllegalNumberOfQuestionsException {
        List<Category> categories = categoryService.findAll();
        Map<String, List<Ranking>> categoryRankings = new HashMap<>();

        for (Category category : categories) {
            categoryRankings.put(
                    category.getName(),
                    userService.findFiveByCategory(CATEGORY_RANKING_LIMIT, category.getId()));
        }
        model.addAttribute("categories", categories);
        model.addAttribute("games", gameService.getAllOpenGames());
        model.addAttribute("generalRank", userService.findGeneralRank(GENERAL_RANKING_LIMIT));
        model.addAttribute("categoryRankings", categoryRankings);
        model.addAttribute("statistics", statService.findStatistic());
        model.addAttribute("numberUsers", statService.findNumberOfUsers());
        return "index";
    }

    // do testow.. wynik gry
    @RequestMapping("/end")
    public String endgame() throws IllegalNumberOfQuestionsException {
        return "check_scores";
    }
}
