package com.pw.quizwhizz.model.entity;

import com.pw.quizwhizz.model.Player;
import com.pw.quizwhizz.model.account.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "player_in_game")
public class PlayerInGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private Player player;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "owner")
    private boolean isOwner;

    public Player getPlayer() {
        if(player==null)
            player  = user.getPlayer();
        return player;
    }
}
