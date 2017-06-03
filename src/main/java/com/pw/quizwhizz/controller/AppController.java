package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.dto.Ranking;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.game.Category;
import com.pw.quizwhizz.service.CategoryService;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Startowy kontroler aplikacji
 * @author Michał Nowiński
 */
@Controller
public class AppController {
    /** Ile ({@value}) graczy ma byc wyswietlanych w rankingu generalnym na stronie glownej */
    private final static int GENERAL_RANKING_LIMIT_MAIN_PAGE = 5;
    /** Ile ({@value}) graczy ma byc wyswietlanych w rankingu generalnym na stronie Top50 */
    private final static int GENERAL_RANKING_LIMIT = 50;
    /** Ile ({@value}) graczy ma byc wyswietlanych w rankingu sortowanym wedlug kategori na stronie glownej */
    private final static int CATEGORY_RANKING_LIMIT_MAIN_PAGE = 3;

    private final CategoryService categoryService;
    private final GameService gameService;
    private final StatService statService;

    @Autowired
    public AppController(CategoryService categoryService, GameService gameService, StatService statService) {
        this.categoryService = categoryService;
        this.gameService = gameService;
        this.statService = statService;
    }

    /**
     * Obsluguje sciezke startowa aplikacji
     * @param model przyjmuje liste wysztskich kategorii, rozpoczate gry, ranking generalny, ranking kategori, statystyki portalu, ilosc zarejestrowanych userow
     * @return zwraca adres strony startowej index
     * @throws IllegalNumberOfQuestionsException
     */
    @RequestMapping("/")
    public String home(Model model) throws IllegalNumberOfQuestionsException {
        List<Category> categories = categoryService.findAll();
        Map<String, List<Ranking>> categoryRankings = new HashMap<>();

        for (Category category : categories) {
            categoryRankings.put(
                    category.getName(),
                    statService.findFiveByCategory(CATEGORY_RANKING_LIMIT_MAIN_PAGE, category.getId()));
        }
        model.addAttribute("categories", categories);
        model.addAttribute("games", gameService.getAllOpenGames());
        model.addAttribute("generalRank", statService.findGeneralRank(GENERAL_RANKING_LIMIT_MAIN_PAGE));
        model.addAttribute("categoryRankings", categoryRankings);
        model.addAttribute("statistics", statService.findStatistic());
        model.addAttribute("numberUsers", statService.findNumberOfUsers());
        return "index";
    }

    /**
     * Obsluguje sciezke do strony rankingu Top50
     * @param model przyjmuje ranking generalny
     * @return zwraca adres strony user_ranking
     */
    @RequestMapping("/app/ranking")
    public String ranking(Model model) {
        model.addAttribute("generalRank", statService.findGeneralRank(GENERAL_RANKING_LIMIT));
        return "user_ranking";
    }
}
