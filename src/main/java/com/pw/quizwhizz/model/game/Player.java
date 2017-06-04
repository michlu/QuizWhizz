package com.pw.quizwhizz.model.game;

import com.pw.quizwhizz.model.exception.IllegalTimeOfAnswerSubmissionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Klasa stanowiaca zbior informacji dotyczacych osoby prowadzacej interakcję z gra.
 *
 * @author Karolina Prusaczyk
 * @see Game
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Player {
    private String name;
    private long id;
    private int xp;
    private int gamesPlayed;
    private Game game;
    private boolean isOwner;

    /**
     * Konstruktor klasy przyjmujacy
     *
     * @param name imię gracza
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Konstruktor klasy przyjmujacy
     *
     * @param name imię gracza oraz
     * @param game instancję gry, z ktora prowadzona jest interakcja.
     *             <p>
     *             Konstruktor dodaje gracza do listy graczy w danej grze i ustala, czy gracz jest
     *             jej wlascicielem - zalozycielem czy tylko jej uczestnikiem.
     */
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;

        if (game.getPlayers().size() == 0) {
            this.isOwner = true;
        } else {
            this.isOwner = false;
        }
        game.addPlayer(this);
    }

    /**
     * Metoda startujaca grę. Jedynie wlasciel gry moze rozpoczac rozgrywke.
     */
    public void startGame() {
        if (this.isOwner)
            game.start();
    }

    /**
     * Metoda przesylajaca do gry listę odpowiedzi do ewaluacji wraz z informacja o graczu.
     *
     * @param answers odpowiedzi gracza
     * @throws IllegalTimeOfAnswerSubmissionException jesli gracz sprobuje wyslac odpowiedzi po uplynięciu okreslonego czasu
     * @see Game#evaluateAnswers(Player, List)
     */
    public void submitAnswers(List<Answer> answers) throws IllegalTimeOfAnswerSubmissionException {
        if (answers == null) {
            return;
        }
        game.evaluateAnswers(this, answers);
    }

    /**
     * Metoda dodajaca graczowi okreslona liczbę punktow doswiadczenia, wykorzystywanych w rankingach.
     *
     * @param xp punkty doswiadzenia przyznane graczowi.
     */
    public void addXp(int xp) {
        this.xp += xp;
    }

    /**
     * Metoda zwiększajaca liczbę zagranych przez gracza gier.
     */
    public void incrementGamesPlayed() {
        this.gamesPlayed += 1;
    }
}