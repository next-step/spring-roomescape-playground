package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest request) {
        Time savedTime = timeService.save(request);

        return ResponseEntity.created(URI.create("/times/" + savedTime.getId()))
                .body(TimeResponse.from(savedTime));
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> readTimeList() {
        List<TimeResponse> timeResponses = timeService.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();
        return ResponseEntity.ok(timeResponses);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
