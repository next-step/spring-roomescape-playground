package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.Time;
import roomescape.exception.IllegalReservationException;
import roomescape.exception.NotFoundReservationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/times")
public class TimeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        try {
            if (time == null || time.getTime() == null) {
                throw new IllegalReservationException("Time의 항목이 채워지지 않았습니다.");
            }
            addTimeToDatabase(time);

            return ResponseEntity
                    .<Time>status(CREATED)
                    .location(java.net.URI.create("/times/" + time.getId()))
                    .body(time);
        } catch (Exception e) {
            return ResponseEntity.<Time>status(HttpStatus.INTERNAL_SERVER_ERROR).body(time);
        }
    }


    @GetMapping
    public List<Time> getTime() {
        return getTimeFromDatabase();
    }

    @GetMapping("/{id}") // 추가: 특정 시간 조회를 위한 엔드포인트
    public Time getTimeById(@PathVariable long id) {
        return getTimeFromDatabase(id);
    }

    private Time getTimeFromDatabase(long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new TimeRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("[ERROR] 해당 ID의 Time이 없음");
        }
    }

    private List<Time> getTimeFromDatabase() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, new TimeRowMapper());
    }

    private static class TimeRowMapper implements RowMapper<Time> {
        @Override
        public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String timeString = rs.getString("time");

            LocalTime time = Time.parseTime(timeString);

            return new Time(id, time);
        }
    }

    private void addTimeToDatabase(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.getTimeAsString());
            return ps;
        }, keyHolder);

        long generatedId = (long) keyHolder.getKey();
        time.setId(generatedId);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        try {
            deleteTimeFromDatabase(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundReservationException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void deleteTimeFromDatabase(long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new NotFoundReservationException("[ERROR] 해당 ID의 Time이 없음");
        }
    }

}
