package roomescape.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<TimeResponse>> getTimes() {

        List<TimeResponse> timeResponses = timeService.getTimes()
                .stream()
                .map(TimeResponse::convert)
                .toList();

        return ResponseEntity.ok().body(timeResponses);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> postTime(
            @Valid @RequestBody TimeRequest timeRequest
    ) {

        Time time = timeService.createTime(
                timeRequest.getTime()
        );

        return ResponseEntity.created(
                        URI.create("/times/" + time.getId()))
                .body(TimeResponse.convert(time));
    }

    @GetMapping("/times/{id}")
    public ResponseEntity<TimeResponse> getTime(
            @PathVariable long id
    ) {

        Time time = timeService.getTime(id);

        return ResponseEntity.ok().body(TimeResponse.convert(time));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable long id
    ) {

        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}
