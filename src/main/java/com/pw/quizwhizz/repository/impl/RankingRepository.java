package com.pw.quizwhizz.repository.impl;

import com.pw.quizwhizz.model.dto.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Udostępnia szczegolowe dane dla zestawienia wyników graczy. Udostepnia metode zwracajaca liste obiektow DTO z danymi dla wszystkich punktacji graczy
 */
@Repository
public class RankingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RankingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String generalRank =
            "SELECT u.url_image, u.first_name, u.games_played, u.player_xp, u.id " +
            "FROM user u " +
            "WHERE u.player_xp > 0 " +
            "ORDER BY u.player_xp DESC " +
            "LIMIT ?;";

    private static final String categoryRank =
            "SELECT user.url_image, user.first_name, count(g.id) gs, sum(score.points) ss, user.id " +
            "FROM " +
            "game g " +
            "INNER JOIN score ON g.id = score.game_id " +
            "INNER JOIN user ON user.id = score.user_id " +
            "INNER JOIN category ON g.category_id = category.id " +
            "WHERE user.player_xp > 0 AND category.id = ? " +
            "GROUP BY user.id " +
            "ORDER BY ss DESC, gs DESC " +
            "LIMIT ?;";

    // wyniki ogolne
    public List<Ranking> findGeneralRank(int limitSearch){
        return jdbcTemplate.query(generalRank, new RankingRowMapper(), limitSearch);
    }

    // wyniki po rodzaju kategorii
    public List<Ranking> findFiveByCategory(int limitSearch , Long categoryId){
        return jdbcTemplate.query(categoryRank, new RankingRowMapper(), categoryId, limitSearch);
    }

    /**
     * Pomocnicza klasa implementujaca interface RowMapper. Mapuje wiersze tabeli sql na obiekt javy.
     */
    class RankingRowMapper implements RowMapper<Ranking> {
        @Override
        public Ranking mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Ranking ranking = new Ranking();
            ranking.setPlayerImageUrl(resultSet.getString(1));
            ranking.setPlayerName(resultSet.getString(2));
            ranking.setGamePlayed(resultSet.getInt(3));
//            ranking.setWinMultiplayer(resultSet.getInt(4));
            ranking.setAllPoints(resultSet.getInt(4));
            ranking.setUserID(resultSet.getLong(5));
            return ranking;
        }
    }
}
