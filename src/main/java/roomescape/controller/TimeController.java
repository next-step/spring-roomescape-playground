package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeList;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {

    private final TimeList times;

    public TimeController(TimeList times) {
        this.times = times;
    }

    @GetMapping("/times")
    public List<TimeResponse> findAll() {
        return times.findAll();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest request) {
        TimeResponse created = times.create(request);
        return ResponseEntity.created(URI.create("/times/" + created.id())).body(created);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        times.delete(id);
        return ResponseEntity.noContent().build();
    }
}


