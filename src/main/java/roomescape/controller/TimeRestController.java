package roomescape.controller;

import jakarta.validation.Valid;
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
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeRestController {

    private final TimeService timeService;

    public TimeRestController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> getTimes() {
        return ResponseEntity.ok(timeService.getAllTimes());
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createTime(
        @RequestBody @Valid TimeRequest request
    ) {
        TimeResponse saved = timeService.createTime(request);
        return ResponseEntity.created(URI.create("/times/" + saved.id())).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletedTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
