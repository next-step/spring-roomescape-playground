package roomescape;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> create(@Valid @RequestBody TimeRequest request) {
        TimeResponse response = timeService.createTime(request);
        return ResponseEntity.created(URI.create("/times/" + response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> readAll() {
        return ResponseEntity.ok(timeService.findAllTimes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
