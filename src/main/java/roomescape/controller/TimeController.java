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

import jakarta.validation.Valid;
import roomescape.controller.dto.TimeCreate;
import roomescape.controller.dto.TimeResponse;
import roomescape.service.TimeService;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String getTime() {
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> getTimes() {
        return ResponseEntity.ok().body(timeService.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> postTime(@RequestBody @Valid TimeCreate request) {
        TimeResponse timeResponse = timeService.add(request);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.id()))
            .body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.remove(id);

        return ResponseEntity.noContent().build();
    }
}
