package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
public class TimeRestController {

    private final TimeService timeService;

    public TimeRestController(TimeService timeService){
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> read() {
        List<TimeResponse> response = timeService.findAll().stream()
                .map(TimeResponse::from)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest request) {
        Time newTime = timeService.save(Time.of(request));

        URI location = URI.create("/times/" + newTime.id());
        return ResponseEntity
                .created(location)
                .body(TimeResponse.from(newTime));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
