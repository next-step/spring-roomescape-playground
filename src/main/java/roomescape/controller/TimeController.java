package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.error.ErrorCode;
import roomescape.error.NotFoundTimeException;

@RestController
public class TimeController {
    private final TimeDAO timeDAO;

    public TimeController(JdbcTemplate jdbcTemplate) {
        timeDAO = new TimeDAO(jdbcTemplate);
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Time time) {
        if(time.getTime() == null) {
            throw new NotFoundTimeException(ErrorCode.INVALID_ARGUMENT);
        }
        Long id = timeDAO.insert(time);
        Time newTime = time.toEntity(id);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.ok(timeDAO.findAllTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        if(timeDAO.delete(id) == 0) {
            throw new NotFoundTimeException(ErrorCode.NOT_EXIST_TIME);
        }
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity handleException(NotFoundTimeException e) {
        return ResponseEntity.badRequest().build();
    }
}
