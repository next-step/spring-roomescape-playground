package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest request) {
        long id = timeService.save(request);
        TimeResponse time = timeService.findById(id);
        URI location = URI.create("/times/" + id);

        return ResponseEntity.created(location).body(time);
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        List<TimeResponse> timeList = timeService.findAll();
        return ResponseEntity.ok(timeList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        long deletedId = timeService.deleteById(id);
        if (deletedId == -1) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}