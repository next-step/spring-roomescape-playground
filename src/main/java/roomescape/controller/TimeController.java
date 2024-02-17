package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.Time.TimeErrorMessage;
import roomescape.exception.Time.TimeException;
import roomescape.repository.TimeDAO;

@Controller
public class TimeController {

    private final TimeDAO timeDAO;

    public TimeController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> read() {
        List<TimeResponse> timeResponses = timeDAO.findAll().stream()
                .map(Time::toResponse)
                .toList();
        return ResponseEntity.ok().body(timeResponses);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest timeRequest) {
        Time time = new Time(timeRequest.time());
        TimeResponse timeResponse = time.toEntity(timeDAO.insert(time)).toResponse();
        return ResponseEntity.created(URI.create("/times/" + timeResponse.id()))
                .body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deleteRows = timeDAO.delete(id);
        if (deleteRows == 0) {
            throw new TimeException(TimeErrorMessage.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}
