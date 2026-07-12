package roomescape.db.time.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.api.exception.NotFoundException;
import roomescape.db.time.entity.TimeEntity;
import roomescape.enums.ErrorMessage;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private static final int DATABASE_ERROR = 0;


    public List<TimeEntity> getTimes() {

        final String sql = "SELECT id, time FROM time";

        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> TimeEntity.builder()
                        .id(resultSet.getLong("id"))
                        .time(resultSet.getString("time"))
                        .build()
        );
    }

    public TimeEntity createTime(final String time) {
        final String sql = "INSERT INTO time(time) VALUES (?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time);
            return ps;
        }, keyHolder);

        final String findSql = "SELECT id, time FROM time WHERE id = (?)";
        return jdbcTemplate.queryForObject(
                findSql,
                (resultSet, rowNum) -> {
                    return TimeEntity.builder()
                            .id(resultSet.getLong("id"))
                            .time(resultSet.getString("time")) // time_id 대신 실제 시간 반환
                            .build();
                },
                keyHolder.getKey().longValue()
        );
    }

    public void deleteTime(final long timeId) {
        final String sql = "DELETE FROM time WHERE id = ?";

        if (jdbcTemplate.update(sql, timeId) == DATABASE_ERROR) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND_RESERVATION);
        }
    }
}
