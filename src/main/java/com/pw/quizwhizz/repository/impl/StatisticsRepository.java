package com.pw.quizwhizz.repository.impl;

import com.pw.quizwhizz.model.dto.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Repozytorium udostępnia statystyki dla portalu, takie jak ilosc rozegranych gier łacznie, gier wieloosobowych, najczesciej wybierana kategoria
 * @author Michał Nowiński
 */
@Repository
public class StatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatisticsRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

    private static final String statisticSQL =
                    "SELECT count(DISTINCT g.id), " +
                    "(SELECT count(*) FROM (SELECT s.game_id FROM score s GROUP BY s.game_id HAVING count(s.user_id) >=2) d1), " +
                    "(SELECT category.category_name FROM category INNER JOIN game ON game.category_id = category.id GROUP BY category_name ORDER BY count(*) DESC LIMIT 1), " +
                    "(SELECT points FROM score GROUP BY points ORDER BY COUNT( * ) DESC LIMIT 1) " +
                    "FROM game g " +
                    "INNER JOIN score ON g.id = score.game_id;";

    public Statistics findStatistic(){
        return jdbcTemplate.queryForObject(statisticSQL, new StatisticRowMapper());
    }

    /**
     * Pomocnicza klasa implementujaca interface RowMapper. Mapuje wiersze tabeli sql na obiekt javy.
     * @see RowMapper
     */
    class StatisticRowMapper implements RowMapper<Statistics> {
        @Override
        public Statistics mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Statistics statistics = new Statistics();
            statistics.setNumberGames(resultSet.getInt(1));
            statistics.setNumberMPGames(resultSet.getInt(2));
            statistics.setMostPopularCategory(resultSet.getString(3));
            statistics.setMostPopularScore(resultSet.getInt(4));
            return statistics;
        }
    }
}