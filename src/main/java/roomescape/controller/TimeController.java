package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.domain.Time;
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
    public ResponseEntity<List<TimeResponse>> read() {
        List<TimeResponse> responses = timeService.read().stream()
                .map(TimeResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@Valid @RequestBody TimeRequest request) {
        Time newTime = timeService.create(request);
        TimeResponse response = TimeResponse.from(newTime);

        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
