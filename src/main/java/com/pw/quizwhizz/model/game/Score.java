package com.pw.quizwhizz.model.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Klasa Score odpowiedzialna za ocenienie odpowiedzi kazdego gracza, oznaczenie najwyzszego wyniku
 * lub najwyzszych wynikow, przyznanie punktow za grę oraz dodatkowych punktow za zwycięstwo w grze wieloosobowej.
 *
 * @author Karolina Prusaczyk
 * @see Player
 */

@Component
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Score {
    private Player player;
    private long gameId;
    private int points;
    private boolean isHighest;

    /**
     * Konstruktor klasy Score przyjmujacy
     *
     * @param player odnoszacy się do gracza, dla ktorego będa wykonywane operacje
     */
    public Score(Player player) {
        this.player = player;
    }

    /**
     * Konstruktor klasy Score korzystajacy z
     *
     * @param builder instancji klasy budujacej Score
     */
    public Score(ScoreBuilder builder) {
        this.player = builder.getPlayer();
        this.gameId = builder.getGameId();
        this.points = builder.getPoints();
        this.isHighest = builder.isHighest();
    }

    /**
     * Metoda wykorzystywana przez instancję gry do obliczenia liczby punktow zdobytych przez gracza.
     * Wywolywane sa dwie metody gracza: dodajaca graczowi obliczona liczbę punktow doswiadczenia
     * oraz inrementujaca liczbę zagranych przez niego gier.
     *
     * @param submittedAnswers - instancji klasy budujacej Score
     */
    void evaluateAnswers(List<Answer> submittedAnswers) {
        for (int i = 0; i < submittedAnswers.size(); i++) {
            Answer answer = submittedAnswers.get(i);
            if (answer.getIsCorrect()) {
                points += 10;
            }
        }
        player.addXp(points);
        player.incrementGamesPlayed();
    }

    /**
     * Metoda wykorzystywana przez instancję gry do oznaczenia danego wyniku jako najwyzszy
     * ze zdobytych w danej rozgrywce oraz dodania zwycięzcy 30 punktow bonusu.
     */
    void markAsHighest() {
        isHighest = true;
        player.addXp(30);
    }
}

