package roomescape.domain.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.dto.CreateTimeRequestDto;
import roomescape.domain.time.entity.Time;
import roomescape.exception.NotFoundTimeException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> timeRowMapper = (rs, rn) -> new Time(
            rs.getLong("id"),
            rs.getString("time")
    );

    public List<Time> findAllTimes() {
        String sql = "select id, time from time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public Time findTimeById(Long id) {
        String sql = "select id, time from time where id = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }


    public Time save(CreateTimeRequestDto dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("time", dto.getTime());

        Number key = jdbcInsert.executeAndReturnKey(params);
        long savedId = Objects.requireNonNull(key).longValue();

        return new Time(savedId, dto.getTime());
    }

    public void delete(Long id) {
        String sql = "delete from time where id = ?";
        int count = jdbcTemplate.update(sql, id);

        if (count == 0) throw new NotFoundTimeException();
    }
}
