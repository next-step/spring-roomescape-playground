package roomescape.domain.time.dao;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.time.domain.Time;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> rowMapper = (resultset, rowNum) -> new Time(
        resultset.getLong("id"),
        LocalTime.parse(resultset.getTime("time").toString())
    );

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> getAllTime() {
        return jdbcTemplate.query("select * from time", rowMapper);
    }

    public Long addTime(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into time(time) values(?)", new String[] {"id"});
            ps.setString(1, time.getTime().toString());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean deleteTime(Long id) {
        int affectRowCount = jdbcTemplate.update("delete from time where id = ?", id);
        return affectRowCount > 0;
    }

    public Time getTimeById(Long id) {
        return jdbcTemplate.queryForObject("select * from time where id = ?", rowMapper, id);
    }
}
