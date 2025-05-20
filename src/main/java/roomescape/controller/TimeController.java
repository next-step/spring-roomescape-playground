package roomescape.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody Map<String, String> request) {
        Time saved = timeService.add(request.get("time"));
        return ResponseEntity.created(URI.create("/times/" + saved.getId()))
                .body(saved);
    }

    @GetMapping
    public List<Time> findAll() {
        return timeService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
