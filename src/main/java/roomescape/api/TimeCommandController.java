package roomescape.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.application.dto.TimeCreateRequest;
import roomescape.application.dto.TimeResponse;
import roomescape.domain.Time;
import roomescape.domain.repository.TimeRepository;

import java.net.URI;

@RestController
@RequestMapping("/times")
public class TimeCommandController {
    private final TimeRepository jdbcTimeRepository;

    public TimeCommandController(final TimeRepository jdbcTimeRepository) {
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody final TimeCreateRequest request) {
        final Time savedTime = jdbcTimeRepository.save(TimeCreateRequest.from(request));
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId()))
                .body(TimeResponse.from(savedTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTime(@PathVariable final Long id) {
        jdbcTimeRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
