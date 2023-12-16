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
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;
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

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@Valid @RequestBody TimeRequest timeRequest) {
        Long id = timeService.createTime(timeRequest);
        TimeResponse timeResponse = timeService.getTime(id);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.getId())).body(timeResponse);
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> getTimes() {
        return ResponseEntity.ok(timeService.getTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
