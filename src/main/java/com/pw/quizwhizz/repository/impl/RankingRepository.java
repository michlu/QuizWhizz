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
 * @author michlu
 * @sience 01.06.2017
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

    public List<Ranking> findGeneralRank(int limitSearch){
        return jdbcTemplate.query(generalRank, new RankingRowMapper(), limitSearch);
    }

    // wyszukiwanie wynikow po rodzaju kategorii
//    public List<Ranking> findFiveByCategory(int limitSearch , Long categoryId){
//        return jdbcTemplate.query(sql1, new RankingRowMapper(), categoryId, limitSearch);
//    }

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
