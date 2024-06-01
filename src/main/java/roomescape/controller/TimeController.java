package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    @Autowired
    private TimeService timeService;

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeService.getAllTimes();
        return ResponseEntity.ok(times);
    }

    @GetMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Time> getTimeById(@PathVariable long id) {
        Time time = timeService.getTimeById(id);
        return ResponseEntity.ok(time);
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<Time> addTime(@Valid @RequestBody Time time) {
        Time newTime = timeService.createTime(time);
        String uri = "/times/" + newTime.getId();
        return ResponseEntity.created(URI.create(uri)).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        timeService.deleteTimeById(id);
        return ResponseEntity.noContent().build();
    }
}
