package com.pw.quizwhizz.model;

import com.pw.quizwhizz.model.Player;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Karolina on 18.04.2017.
 */
public class PlayerTest {

    @Test
    public void testEquals() {
        Player player1 = new Player("Janek Jankowski");
        Player player2 = new Player("Janek Jankowski");
        Player player3 = new Player("Janek Jankowski");

        player1.incrementGamesPlayed();
        player2.incrementGamesPlayed();

        player1.addXp(20);
        player2.addXp(20);

        assertThat(player1.equals(player2));
        assertThat(!player3.equals(player1));
    }

    @Test
    public void testHashCode() throws Exception {
        Player player1 = new Player("Joanna Janicka");
        Player player2 = new Player("Joanna Janicka");
        Player player3 = new Player("Joanna Janicka");

        player1.addXp(100);
        player2.addXp(100);

        player1.incrementGamesPlayed();
        player2.incrementGamesPlayed();

        assertThat(player1.hashCode()).isEqualTo(player2.hashCode());
        assertThat(player3.hashCode()).isNotEqualTo(player1.hashCode());
    }

    @Test
    public void testGettersAddXpAndIncrementGamesPlayedMethods() {
        Player player1 = new Player("Jerzy Jarzyna");

        player1.incrementGamesPlayed();
        player1.incrementGamesPlayed();

        player1.addXp(200);
        player1.addXp(20);

        assertThat(player1.getName()).isEqualTo("Jerzy Jarzyna");
        assertThat(player1.getGamesPlayed()).isEqualTo(2);
        assertThat(player1.getXp()).isEqualTo(220);
    }

}