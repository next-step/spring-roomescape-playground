package roomescape.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.TimeSlot;
import roomescape.exception.DuplicateTimeSlotException;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeSlotRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeSlotRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TimeSlot> findAll() {
        String sql = "SELECT id, start_at FROM time_slot";
        return jdbcTemplate.query(sql, timeSlotRowMapper);
    }

    public TimeSlot save(TimeSlot timeSlot) {
        String sql = "INSERT INTO time_slot (start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});

                ps.setObject(1, timeSlot.getStartAt());
                return ps;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            throw new DuplicateTimeSlotException("이미 등록된 시간입니다. time=" + timeSlot.getStartAt());
        }

        Long id = keyHolder.getKey().longValue();

        return timeSlot.withId(id);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM time_slot WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private final RowMapper<TimeSlot> timeSlotRowMapper = (resultSet, rowNum) -> {
        TimeSlot timeSlot = new TimeSlot(
                resultSet.getObject("start_at", LocalTime.class)
        );
        return timeSlot.withId(resultSet.getLong("id"));
    };
}
