package hello.repository;

import hello.domain.Time;
import hello.exceptions.ReferencedTimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class TimeRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Time> timeRowMapper = (rs, rn) -> new Time(
            rs.getLong("id"),
            rs.getTime("time").toLocalTime()
    );

    public List<Time> findAllTimes() {
        String sql = "select id, time from time";
        return template.query(sql, timeRowMapper);
    }

    public Time findById(Long id) {
        String sql = "select id, time from time where id = ?";
        return template.queryForObject(sql, timeRowMapper, id);
    }

    public Time save(Time time) {

        Map<String, Object> params = new HashMap<>();
        params.put("time", time.getTime());

        Number key = jdbcInsert.executeAndReturnKey(params);
        long savedId = Objects.requireNonNull(key).longValue();

        return new Time(savedId, time.getTime());
    }

    public int delete(Long id) {
        try {
            String sql = "delete from time where id = ?";
            return template.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            // 참조하는 레코드가 있을 때의 예외 처리
            throw new ReferencedTimeException();
        }
    }
}