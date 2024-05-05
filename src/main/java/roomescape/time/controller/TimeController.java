package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.Time;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {

    private TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@RequestBody Time time) {
        Time newTime = timeService.createTime(time);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        return ResponseEntity.ok().body(timeService.findTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}
