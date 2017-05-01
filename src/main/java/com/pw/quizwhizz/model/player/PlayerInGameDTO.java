package com.pw.quizwhizz.model.player;

import com.pw.quizwhizz.model.account.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "player_in_game")
public class PlayerInGameDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "game_id")
    private long gameId;

    @Column(name = "owner")
    private boolean isOwner;

    @Transient
    private Player player;

    public Player getPlayer() {
        if(player==null)
            player  = user.getPlayer();
        return player;
    }
}
