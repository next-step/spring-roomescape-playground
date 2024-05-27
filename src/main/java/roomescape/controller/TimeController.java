package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Time;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Controller
public class TimeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
        time.setId(generatedId);
        URI location = URI.create("/times/" + generatedId);
        return ResponseEntity.created(location).body(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        String sql = "SELECT * FROM time";
        List<Time> timeSlots = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Time(rs.getLong("id"), rs.getString("time"))
        );
        return ResponseEntity.ok(timeSlots);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
