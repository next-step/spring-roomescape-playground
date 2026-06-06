package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public List<TimeResponse> showTimes() {
        return timeService.findAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody @Valid TimeRequest request) {
        Time savedTime = timeService.create(request.time());

        return ResponseEntity
                .created(URI.create("/times/" + savedTime.getId()))
                .body(TimeResponse.from(savedTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
