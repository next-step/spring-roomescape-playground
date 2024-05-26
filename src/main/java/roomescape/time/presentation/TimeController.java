package roomescape.time.presentation;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeRepository;
import roomescape.time.presentation.dto.AddTimeRequest;
import roomescape.time.presentation.dto.TimeResponse;

@RequestMapping("/times")
@RestController
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        final List<Time> times = timeRepository.findAll();
        final List<TimeResponse> responses = times.stream()
                                                  .map(TimeResponse::from)
                                                  .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody final AddTimeRequest request) {
        final Time savedTime = timeRepository.save(request.toTime());
        final TimeResponse response = TimeResponse.from(savedTime);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create("/times/" + response.id()))
                             .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable final Long id) {
        timeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
