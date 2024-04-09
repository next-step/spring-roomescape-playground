package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeDAO {
    private JdbcTemplate jdbcTemplate;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time findTimeById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, time FROM time where id = ?",
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                }, id);
    }

    public List<Time> findAllTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                });
    }

    public Long insertWithKeyHolder(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO time (time) VALUES (?)",
                    new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return id;
    }

    public void insert(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        jdbcTemplate.update(sql, time.getTime());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

}