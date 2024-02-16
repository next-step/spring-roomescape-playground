package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

@Controller
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String time() { return "time"; }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> read() {
        List<TimeResponse> timeResponses = timeService.findAllTimes()
                .stream()
                .map(TimeResponse::new)
                .toList();
        return ResponseEntity.ok().body(timeResponses);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest timeRequest) {
        TimeResponse timeResponse = timeService.insertNewTime(timeRequest);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.getId())).body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
