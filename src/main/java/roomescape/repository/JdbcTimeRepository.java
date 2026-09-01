package roomescape.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcTimeRepository implements TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existsByStartAt(LocalTime startAt) {
        String sql = "select exists (select 1 from times where start_at = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, startAt);
    }

    @Override
    public Time save(Time time) {
        String sql = "insert into times (start_at) values(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setObject(1, time.getStartAt());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException exception) {
            throw new TimeException(TimeErrorCode.TIME_CONFLICT);
        }

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException("시간대 저장 후 생성된 ID를 확인할 수 없습니다.");
        }

        return new Time(key.longValue(), time.getStartAt());
    }

    @Override
    public List<Time> findAll() {
        String sql = "select id, start_at from times " +
                "order by start_at";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    private Time mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Time(
                resultSet.getLong("id"),
                resultSet.getObject("start_at", LocalTime.class)
        );
    }
}
