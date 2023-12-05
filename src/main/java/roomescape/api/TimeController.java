package roomescape.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.TimeCommandService;
import roomescape.application.TimeQueryService;
import roomescape.application.dto.TimeCreateRequest;
import roomescape.application.dto.TimeResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeCommandService timeCommandService;
    private final TimeQueryService timeQueryService;

    public TimeController(final TimeCommandService timeCommandService, final TimeQueryService timeQueryService) {
        this.timeCommandService = timeCommandService;
        this.timeQueryService = timeQueryService;
    }

    @GetMapping
    public List<TimeResponse> findTimes() {
        return timeQueryService.findTimes();
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody final TimeCreateRequest request) {
        final TimeResponse response = timeCommandService.addTime(request);
        return ResponseEntity.created(URI.create("/times/" + response.getId()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTime(@PathVariable final Long id) {
        timeCommandService.removeTime(id);
        return ResponseEntity.noContent().build();
    }
}
