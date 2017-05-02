package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.model.Game;
import com.pw.quizwhizz.model.player.Player;
import com.pw.quizwhizz.model.PlayerInGame;
import com.pw.quizwhizz.repository.GameRepository;
import com.pw.quizwhizz.repository.PlayerInGameRepository;
import com.pw.quizwhizz.repository.UserRepository;
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
    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;

    private PlayerInGameService testedService;

    @Before
    public void setup() {
        testedService = new PlayerInGameServiceImpl(repository, userRepository, gameRepository);
    }

    @Test
    public void testConvertToPlayerInGame() throws Exception {

        Player player = new Player("Player");
        Game game = mock(Game.class);

        PlayerInGame playerInGame =  testedService.convertToPlayerInGame(player, game);

        assertThat(playerInGame).isNotNull();
        assertThat(playerInGame.getName()).isEqualTo(player.getName());
        assertThat(playerInGame.getGame()).isEqualTo(game);
        assertThat(playerInGame.getId()).isEqualTo(player.getId());
    }

}