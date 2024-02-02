package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
        return time;
    };

    public List<Time> findAllTimes() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Optional<Time> findTimeById(long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        try {
            Time time= jdbcTemplate.queryForObject(sql, timeRowMapper, id);
            return Optional.ofNullable(time);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int delete(long id) {
        String sql = "delete from time where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public long insertWithKeyHolder(TimeRequestDto timeDto) {
        String sql = "insert into time (time) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into time (time) values (?)",
                    new String[]{"id"});
            ps.setString(1, timeDto.time());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return id;
    }
}
