package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.model.exception.IllegalNumberOfQuestionsException;
import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import com.pw.quizwhizz.model.exception.ScoreCannotBeRetrievedBeforeGameIsClosedException;
import com.pw.quizwhizz.model.game.Game;
import com.pw.quizwhizz.model.game.Question;
import com.pw.quizwhizz.model.game.Score;
import com.pw.quizwhizz.service.GameService;
import com.pw.quizwhizz.service.QuestionService;
import com.pw.quizwhizz.service.UserService;
import com.pw.quizwhizz.service.exception.NoQuestionsInDBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler obslugujacy zapytania dotyczace gier.
 * Umozliwia stworzenie i dolaczenie do gry, wyslanie odpowiedzi i odebranie wynikow.
 *
 * @author Karolina Prusaczyk
 */

@Controller
@RequestMapping("/game")
public class GameController {
    final private GameService gameService;
    final private UserService userService;
    final private QuestionService questionService;

    /**
     * Zaleznosci kontrolera, rozwiazywane automatycznie przez Springa
     *
     * @param gameService     serwis gry stanowiacy częsc logiki biznesowej
     * @param userService     serwis uzytkownika sluzacy identyfikacji graczy
     * @param questionService serwis pytań niezbędny do uzyskania zestawu pytań w celu stworzenia nowej instancji gry
     */
    @Autowired
    public GameController(GameService gameService, UserService userService, QuestionService questionService) {
        this.gameService = gameService;
        this.userService = userService;
        this.questionService = questionService;
    }

    /**
     * Metoda tworzaca nowa instancję gry wraz z pytaniami z wybranej kategorii
     * i identyfikujaca "wlasciciela" - zalozyciela gry.
     *
     * @param categoryId     id kategorii, w ktorej tworzymy gre
     * @param model          model, ktorego atrybuty będa wyswietlane na widoku
     * @param authentication interfejs Springa pozwalajacy na zidentyfikowanie uzytkownika w kontekscie aplikacji
     * @return widok otwartej gry
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/open/forCategory/{categoryId}")
    public String createGame(@PathVariable String categoryId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        List<Question> questions;
        try {
            questions = questionService.getQuestionsForNewGame(Long.parseLong(categoryId));
        } catch (NoQuestionsInDBException e) {
            return "redirect:/";
        }
        Game game = gameService.createGame(questions);
        User user = userService.findByEmail(authentication.getName());
        gameService.addOwnerToGame(game, user);

        fillModelForOpenGamePage(model, game, true);
        return "open_game";
    }

    /**
     * Metoda umozliwiajaca dolaczenie do otwartej gry.
     *
     * @param gameId         id gry, do ktorej chcemy dolaczyc
     * @param model          model, ktorego atrybuty będa wyswietlane na widoku
     * @param authentication interfejs Springa pozwalajacy na zidentyfikowanie uzytkownika w kontekscie aplikacji
     * @return widok otwartej gry
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/{gameId}/joinOpened")
    public String joinOpenedGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);

        gameService.addPlayerToGame(game, user);
        boolean isOwner = gameService.isPlayerGameOwner(user.getId(), gameId);

        fillModelForOpenGamePage(model, game, isOwner);
        return "open_game";
    }

    /**
     * Metoda startujaca grę. Jedynie "wlasciciel" - zalozyciel gry ma prawo rozpoczac grę.
     * Pozostali gracze nie maja dostępu do przycisku start i zostaja przekierowani na stronę rozpoczętej gry
     * w momencie, kiedy zalozyciel go nacisnie.
     *
     * @param gameId         id gry, ktora ma zostac rozpoczeta
     * @param model          model, ktorego atrybuty będa wyswietlane na widoku
     * @param authentication interfejs Springa pozwalajacy na zidentyfikowanie uzytkownika w kontekscie aplikacji
     * @return widok rozpoczętej gry
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/{gameId}/start")
    public String startGame(@PathVariable Long gameId, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);

        gameService.startGame(game, user);

        fillModelForStartedGamePage(model, game);
        return "started_game";
    }

    /**
     * Metoda sprawdzajaca imiona graczy, ktorzy dolaczyli do rozgrywki, celem wyswietlenia ich
     * na stronie gry oczekujacej na rozpoczęcie.
     *
     * @param gameId id gry oczekujacej na start
     * @return obiekt JSON skladajacy się z imion graczy, ktorzy dolaczyli do gry
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/{gameId}/getNamesOfPlayers", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getNamesOfPlayers(@PathVariable Long gameId) throws IllegalNumberOfQuestionsException {
        List<String> names = gameService.getNamesOfPlayersInGame(gameId);

        String playersJson = "[";
        for (String name : names) {
            playersJson += "{ \"name\" : \"" + name + "\" }, ";
        }
        playersJson = playersJson.replaceAll(", $", "");
        playersJson += "]";

        return playersJson;
    }

    /**
     * Metoda sprawdzajaca, czy gra zostala rozpoczeta
     *
     * @param gameId id gry, ktorej status sprawdzamy
     * @return obiekt JSON skladajacy się z pola "isStarted" oraz wartosci true lub false
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/{gameId}/isStarted", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String isGameStarted(@PathVariable Long gameId) throws IllegalNumberOfQuestionsException {
        boolean isStarted = gameService.isGameStarted(gameId);

        return "{ \"isStarted\" : " + isStarted + " }";
    }

    /**
     * Metoda przekierowujaca gracza na stronę rozpoczętej gry, po wciscięciu guzika start przez zalozyciela gry
     *
     * @param gameId id rozpoczynanej gry
     * @param model  model, ktorego atrybuty będa wyswietlane na widoku
     * @return widok rozpoczętej gry
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/{gameId}/joinStarted")
    public String joinStartedGame(@PathVariable Long gameId, Model model) throws IllegalNumberOfQuestionsException {
        Game game = gameService.findGameById(gameId);

        fillModelForStartedGamePage(model, game);
        return "started_game";
    }

    /**
     * Metoda przekierowujaca po okreslonym czasie na stronę odpowiedzi, w przypadku kiedy gracz
     * nie odpowiedzial na zadne pytanie - zabezpieczenie przed blędami HTTP.
     *
     * @param gameId id rozgrywanej gry
     * @param model  model, ktorego atrybuty będa wyswietlane na widoku
     * @return widok wyslanych odpowiedzi
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/{gameId}/submitAnswers")
    public String submitAnswers(@PathVariable Long gameId, Model model) throws IllegalNumberOfQuestionsException {
        model.addAttribute("gameId", gameId);
        return "submit_answers";
    }

    /**
     * Metoda obsugujaca wyslanie wybranych odpowiedzi przez danego gracza.
     *
     * @param gameId         id rozgrywanej gry
     * @param answers        napis skladajacy się z id wybranych odpowiedzi, oddzielonych przecinkami
     * @param model          model, ktorego atrybuty będa wyswietlane na widoku
     * @param authentication interfejs Springa pozwalajacy na zidentyfikowanie uzytkownika w kontekscie aplikacji
     * @return widok wyslanych odpowiedzi
     * @throws IllegalNumberOfQuestionsException      w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     * @throws IllegalTimeOfAnswerSubmissionException jesli gracz sprobuje wyslac odpowiedzi po uplynięciu okreslonego czasu
     */
    @RequestMapping(value = "/{gameId}/submitAnswers/{answers}")
    public String submitAnswers(@PathVariable Long gameId, @PathVariable String answers, Model model, Authentication authentication) throws IllegalNumberOfQuestionsException, IllegalTimeOfAnswerSubmissionException {
        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.findGameById(gameId);
        List<Long> answerIds = new ArrayList<>();

        if (answers != null || answers != "") {
            for (String answerId : answers.split(",")) {
                System.out.println(answerId);
                long id = Long.parseLong(answerId);
                answerIds.add(id);
            }
            gameService.submitAnswers(game, user, answerIds);
        }
        model.addAttribute("gameId", gameId);
        return "submit_answers";
    }

    /**
     * Metoda sprawdzajaca, czy gra się zakonczyla. Wyniki gry wraz z wylonionym zwycięzca mozna uzyskac
     * jedynie z zakonczonej gry.
     *
     * @param gameId id sprawdzanej gry
     * @return obiekt JSON skladajacy się z pola "isClosed" oraz wartosci true lub false
     * @throws IllegalNumberOfQuestionsException w przypadku gdy serwis zwroci niewystarczajaca liczbę pytan dla gry
     */
    @RequestMapping(value = "/{gameId}/isClosed", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String isGameClosed(@PathVariable Long gameId) throws IllegalNumberOfQuestionsException {
        boolean isClosed = gameService.isGameClosed(gameId);
        return "{ \"isClosed\" : " + isClosed + " }";
    }

    /**
     * Metoda pobierajaca ostateczne wyniki zakończonej rozgrywki.
     *
     * @param gameId id gry, dla ktorej pobieramy wyniki
     * @param model          model, ktorego atrybuty będa wyswietlane na widoku
     * @param authentication interfejs Springa pozwalajacy na zidentyfikowanie uzytkownika w kontekscie aplikacji
     * @return widok wynikow lub wyslanych odpowiedzi w przypadku przechwycenia wyjatku
     * @throws ScoreCannotBeRetrievedBeforeGameIsClosedException w przypadku gdy nastpi zapytanie o wyniki przed końcem gry
     */
    @RequestMapping(value = "/{gameId}/checkScores")
    public String checkScores(@PathVariable Long gameId, Model model, Authentication authentication) throws ScoreCannotBeRetrievedBeforeGameIsClosedException, IllegalNumberOfQuestionsException {
        User user = userService.findByEmail(authentication.getName());
        List<Score> scores;

        try {
            scores = gameService.checkScores(gameId);
        } catch (ScoreCannotBeRetrievedBeforeGameIsClosedException e) {
            return "submit_answers";
        }
        model.addAttribute("scores", scores);
        return "check_scores";
    }


    private void fillModelForOpenGamePage(Model model, Game game, boolean isOwner) {
        model.addAttribute("game", game);
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("isOwner", isOwner);
    }

    private void fillModelForStartedGamePage(Model model, Game game) {
        model.addAttribute("game", game);
        model.addAttribute("players", game.getPlayers());
        model.addAttribute("questions", game.getQuestions());
    }
}
