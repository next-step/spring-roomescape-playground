package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
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
    public ResponseEntity<List<Time>> getTimes() {

        List<Time> times = timeService.getTimes();

        return ResponseEntity.ok().body(times);
    }

    @PostMapping("/times")
    public ResponseEntity<Time> postTime(
            @RequestBody TimeRequest timeRequest
    ) {

        Time time = timeService.createTime(
                timeRequest.getTime()
        );

        return ResponseEntity.created(
                        URI.create("/times/" + time.getId()))
                .body(time);
    }

    @GetMapping("/times/{id}")
    public ResponseEntity<Time> getTime(
            @PathVariable long id
    ) {

        Time time = timeService.getTime(id);

        return ResponseEntity.ok().body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable long id
    ) {

        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}
