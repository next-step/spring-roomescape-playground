package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
        return time;
    };

    public List<Time> findAllTimes() {
        String sql = "select * from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Optional<Time> findTimeById(Long id) {
        String sql = "select * from time where id = ?";
        try {
            Time time= jdbcTemplate.queryForObject(sql, timeRowMapper, id);
            return Optional.ofNullable(time);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int delete(Long id) {
        String sql = "delete from time where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Long insertTimeId(TimeRequestDto timeDto) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", timeDto.time());

        Number newId = jdbcInsert.executeAndReturnKey(new HashMap<>(parameters));

        return newId.longValue();
    }
}
