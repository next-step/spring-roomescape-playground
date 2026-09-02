package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.time.TimeRequest;
import roomescape.domain.time.Time;
import roomescape.service.TimeService;
import java.net.URI;
import java.util.List;


@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        return ResponseEntity.ok(timeService.read());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@Valid @RequestBody TimeRequest request) {
        Time newTime = timeService.create(request);

        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
