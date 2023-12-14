package roomescape.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Time;
import roomescape.exception.IdNotExistException;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource()))
            .withTableName("time")
            .usingGeneratedKeyColumns("id");
    }

    public List<Time> getAllTimes() {
        return jdbcTemplate.query("SELECT * FROM time", new BeanPropertyRowMapper<>(Time.class));
    }

    public Long saveTime(Time request) {
        validateSaveTime(request);
        var source = new BeanPropertySqlParameterSource(request);
        Number id = simpleJdbcInsert.executeAndReturnKey(source);
        request.setId(id.longValue());
        return id.longValue();
    }

    private void validateSaveTime(Time request) {
        if (request == null || request.getTime() == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
    }

    public void removeTime(Long request) {
        if (!existsById(request)) {
            throw new IdNotExistException();
        }
        jdbcTemplate.update("DELETE time WHERE id = ?", request);
    }

    private boolean existsById(Long id) {
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM time WHERE id = " + id + ")",
            Boolean.class)
        );
    }
}
