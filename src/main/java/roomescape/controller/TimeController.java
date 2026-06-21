package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
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
    public String timePage() {
        return "time";
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeResponse> get() {
        return timeService.getAll();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@Valid @RequestBody TimeRequest timeRequest) {
        TimeResponse timeResponse = timeService.insertTime(timeRequest);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.id()))
                .body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
