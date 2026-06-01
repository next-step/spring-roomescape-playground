package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.TimeCreateRequest;
import roomescape.dto.response.TimeCreateResponse;
import roomescape.dto.response.TimeGetResponse;
import roomescape.service.TimeService;

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
    public List<TimeGetResponse> getTimes() {
        return timeService.getTimes();
    }

    @PostMapping
    public ResponseEntity<TimeCreateResponse> addTime(@RequestBody @Valid TimeCreateRequest request) {
        TimeCreateResponse response = timeService.addTime(request);
        return ResponseEntity.created(URI.create("times/" + response.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
