package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeCreateForm;
import roomescape.dto.TimeResponseForm;
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
    public String time() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponseForm>> getTimes() {
        return ResponseEntity.ok(timeService.getTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponseForm> createTime(@Valid @RequestBody TimeCreateForm timeRequest) {
        Long id = timeService.createTime(timeRequest);
        TimeResponseForm timeResponse = timeService.getTime(id);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.getId())).body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }

}

