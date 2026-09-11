package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {

        List<Time> times = timeRepository.getTimes();

        return ResponseEntity.ok().body(times);
    }

    @PostMapping("/times")
    public ResponseEntity<Time> postTime(
            @RequestBody TimeRequest timeRequest
    ) {

        if (timeRequest.getTime() == null) {
            throw new IllegalArgumentException();
        }

        Time time = timeRepository.saveTime(timeRequest.getTime());

        return ResponseEntity.created(
                        URI.create("/times/" + time.getId()))
                .body(time);
    }

    @GetMapping("/times/{id}")
    public ResponseEntity<Time> getTime(@PathVariable long id) {

        Time time = timeRepository.getTime(id)
                .orElseThrow(NoSuchElementException::new);

        return ResponseEntity.ok().body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {

        int deletedCount = timeRepository.deleteTime(id);

        if (deletedCount == 0) {
            throw new NoSuchElementException();
        }

        return ResponseEntity.noContent().build();
    }
}
