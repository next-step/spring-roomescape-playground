package roomescape.time;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.time.dto.TimeCreateRequest;
import roomescape.time.dto.TimeResponse;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String reservationPage() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<Collection<TimeResponse>> getTimes() {

        return ResponseEntity.ok(timeService.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeCreateRequest request) {
        TimeResponse response = timeService.create(request);

        return ResponseEntity
                .created(URI.create("/times/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
