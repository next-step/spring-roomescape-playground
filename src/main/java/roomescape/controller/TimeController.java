package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.CreateTimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private static final String LOCATION_DEFAULT_VALUE = "/times/";

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(final @RequestBody CreateTimeRequest request) {
        final TimeResponse response = timeService.createTime(request);
        return ResponseEntity.created(URI.create(LOCATION_DEFAULT_VALUE + response.id()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        final List<TimeResponse> responses = timeService.getTimes();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(final @PathVariable long timeId) {
        timeService.deleteTime(timeId);
        return ResponseEntity.noContent()
                .build();
    }
}
