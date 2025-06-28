package roomescape.time.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.exception.TimeNotFoundException;
import roomescape.time.model.Time;
import roomescape.time.request.TimeRequest;
import roomescape.time.response.TimeResponse;

@RestController
@RequestMapping("/times")
public class TimeRestController {

    private final JdbcTemplate jdbcTemplate;

    public TimeRestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<Time> times = jdbcTemplate.query(
            "select id, time from time",
            (resultSet, rowNum) -> {
                return new Time(
                    resultSet.getLong("id"),
                    resultSet.getTime("time").toLocalTime()
                );
            }
        );

        return ResponseEntity.ok(
            times.stream()
                .map(TimeResponse::new)
                .toList()
        );
    }

    @PostMapping
    public ResponseEntity<TimeResponse> postTime(
        @RequestBody TimeRequest request
    ) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("time")
            .usingGeneratedKeyColumns("id");

        insert.compile();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", request.getTime());

        Long id = (Long) insert.executeAndReturnKey(parameters);

        Time time = new Time(
            id,
            request.getTime()
        );

        return ResponseEntity
            .created(URI.create("/times/" + id))
            .body(new TimeResponse(time));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(
        @PathVariable Long id
    ) {
        boolean removed = jdbcTemplate.update(
            "DELETE FROM time WHERE id = ?", id
        ) > 0;

        if (!removed) {
            throw new TimeNotFoundException("Time not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
