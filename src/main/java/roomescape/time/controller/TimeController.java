package roomescape.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.dto.TimeCreationRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTime() {
        List<TimeResponse> responses = timeService.getTimeList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeCreationRequest request) {
        TimeResponse response = timeService.addTime(request);

        return ResponseEntity.created(URI.create("/times/" + response.id)).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}