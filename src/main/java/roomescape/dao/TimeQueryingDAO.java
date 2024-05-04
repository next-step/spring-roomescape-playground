package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeQueryingDAO {

    private JdbcTemplate jdbcTemplate;

    public TimeQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time insertTime(String time) {
        String sql = "insert into time (time) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time);
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Time(id, time);
    }

    public List<Time> findAllTime() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                }
        );
    }

    public Time findTimeById(Long id){
        String sql = "select id, time from time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                }, id);

    }

    public int deleteTime(Long id) {
        String sql = "delete from time where id =?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
