package roomescape;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.time.LocalTime;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTimeResponse> responses = timeService.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(
            @Valid @RequestBody TimeRequest request
    ) {
        ReservationTime time = timeService.create(
                LocalTime.parse(request.getTime())
        );
        return ResponseEntity
                .created(URI.create("/times/" + time.getId()))
                .body(ReservationTimeResponse.from(time));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
