package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.repository.PlayerInGameRepository;
import com.pw.quizwhizz.service.PlayerInGameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by Karolina on 30.04.2017.
 */
public class PlayerInGameServiceImplTest {
    @Mock
    private PlayerInGameRepository repository;
    private PlayerInGameService service;

    @Before
    public void setup() {
        service = new PlayerInGameServiceImpl(repository);
    }

    @Test
    public void testConvertToPlayerInGame() throws Exception {

        Player player = new Player("Player");
        Game game = mock(Game.class);

        PlayerInGame playerInGame =  service.convertToPlayerInGame(player, game);

        assertThat(playerInGame).isNotNull();
        assertThat(playerInGame.getName()).isEqualTo(player.getName());
        assertThat(playerInGame.getGame()).isEqualTo(game);
        assertThat(playerInGame.getId()).isEqualTo(player.getId());
    }

}