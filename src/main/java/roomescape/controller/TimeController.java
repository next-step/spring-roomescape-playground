package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private TimeService timeService;
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }
    @GetMapping("/time")
    public String time(){
        return "time";
    }
    @GetMapping("/times")
    public ResponseEntity<List<Time>> getAllTimes(){
        List<Time> time = timeService.getAllTimes();
        return ResponseEntity.ok().body(time);
    }
    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        timeService.saveTime(time);
        return ResponseEntity.created(URI.create("/times/" +time.getId())).build();
    }
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
