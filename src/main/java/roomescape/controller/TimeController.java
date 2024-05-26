package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.service.TimeService;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String times() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Time request) {
        long id = timeService.save(request);
        Time time = timeService.findById(id);
        URI location = URI.create("/times/" + id);

        return ResponseEntity.created(location).body(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> timeList = timeService.findAll();
        return ResponseEntity.ok(timeList);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        long deletedId = timeService.deleteById(id);
        if (deletedId == -1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}