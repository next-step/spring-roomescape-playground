package roomescape.controller;

import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public List<TimeResponse> findAll() {
        return timeService.findAll();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@Valid @RequestBody TimeRequest request) {
        TimeResponse created = timeService.create(request);
        return ResponseEntity.created(URI.create("/times/" + created.id())).body(created);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


