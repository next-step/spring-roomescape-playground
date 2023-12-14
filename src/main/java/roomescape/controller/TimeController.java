package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.controller.dto.CreateReservationTime;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.Time;
import roomescape.service.TimeService;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String page() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getAll() {
        List<ReservationTimeResponse> response = timeService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> create(@Valid @RequestBody CreateReservationTime request) {
        Time response = timeService.create(request);
        return ResponseEntity.created(URI.create("/times/" + response.getId()))
            .body(ReservationTimeResponse.from(response));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        timeService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
