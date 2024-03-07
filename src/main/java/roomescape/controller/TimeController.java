package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.controller.dto.TimeCreate;
import roomescape.controller.dto.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        return ResponseEntity.ok().body(timeService.findAll());
    }

    @PostMapping
    public ResponseEntity<TimeResponse> postTime(@RequestBody @Valid TimeCreate request) {
        TimeResponse timeResponse = timeService.add(request);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.id()))
            .body(timeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.remove(id);

        return ResponseEntity.noContent().build();
    }
}
