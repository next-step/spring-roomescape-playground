package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.service.TimeService;
import roomescape.model.Time;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String TimePage() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTime() {
        return ResponseEntity.ok(this.timeService.getTime());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Map<String, String> params) {
        String timeValue = params.get("time");
        Time createTime = timeService.addTime(timeValue);
        URI location = URI.create("/times/" + createTime.id());
        return ResponseEntity.created(location).body(createTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable int id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}

