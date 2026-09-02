package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String showTimePage() {
        return "time";
    }

    @ResponseBody
    @PostMapping("/times")
    public ResponseEntity<Time> create(@RequestBody TimeRequest request) {
        Time createdTime = timeService.save(new Time(request.getParsedTime()));
        return ResponseEntity.created(URI.create("/times/" + createdTime.getId())).body(createdTime);
    }

    @ResponseBody
    @GetMapping("/times")
    public ResponseEntity<List<Time>> findAll() {
        List<Time> times = timeService.findAll();
        return ResponseEntity.ok(times);
    }

    @ResponseBody
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
