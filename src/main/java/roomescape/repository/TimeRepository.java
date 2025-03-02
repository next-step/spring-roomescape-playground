package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeRepository {

    private static final RowMapper<Time> TIME_ROW_MAPPER = (rs, rowNum) ->
            new Time(
                    rs.getLong("id"),
                    rs.getTime("time").toLocalTime()
            );

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TIME")
                .usingGeneratedKeyColumns("id");
    }

    public Time save(final Time time) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(time);
        final long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Time(
                id,
                time.getTime()
        );
    }

    public boolean existsByTime(final LocalTime time) {
        final String selectByTime = """
                SELECT EXISTS (
                SELECT 1
                FROM time
                WHERE `time` = ?)
                """;
        return jdbcTemplate.queryForObject(selectByTime, Boolean.class, time);
    }

    public List<Time> findAll() {
        final String selectAll = "SELECT id, time FROM time";
        return jdbcTemplate.query(selectAll, TIME_ROW_MAPPER);
    }

    public Optional<Time> findById(final long timeId) {
        try {
            final String selectById = "SELECT id, time FROM time WHERE id = ?";
            final Time time = jdbcTemplate.queryForObject(selectById, TIME_ROW_MAPPER, timeId);
            return Optional.ofNullable(time);
        } catch (final EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    public void deleteById(final long timeId) {
        final String deleteById = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(deleteById, timeId);
    }
}
