package roomescape.time.controller;

import jakarta.annotation.Nonnull;
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
import roomescape.time.request.TimeRequest;
import roomescape.time.response.TimeResponse;
import roomescape.time.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeRestController {

    private final TimeService timeService;

    public TimeRestController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        return ResponseEntity.ok(timeService.getTimes());
    }

    @PostMapping
    public ResponseEntity<TimeResponse> create(
        @RequestBody TimeRequest request
    ) {
        TimeResponse response = timeService.create(request.time());

        return ResponseEntity
            .created(URI.create("/times/" + response.id()))
            .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id
    ) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
