package roomescape.time.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public List<TimeResponse> getTimes() {
        return timeService.getAllTimes().stream()
                .map(TimeResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(@Valid @RequestBody TimeRequest request) {
        Time newTime = timeService.createTime(request);
        return ResponseEntity
                .created(URI.create("/times/" + newTime.getId()))
                .body(TimeResponse.from(newTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
