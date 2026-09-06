package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
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
    public List<TimeResponse> getTimes() {
        return timeService.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(
            @RequestBody TimeRequest request
    ) {
        Time saved = timeService.save(request.toTime());

        return ResponseEntity
                .created(URI.create("/times/" + saved.getId()))
                .body(TimeResponse.from(saved));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable Long id
    ) {
        timeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
