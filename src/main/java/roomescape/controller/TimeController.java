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
import roomescape.controller.dto.RequestTime;
import roomescape.controller.dto.ResponseTime;
import roomescape.domain.Time;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(final TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ResponseTime> createTime(@RequestBody RequestTime requestTime) {
        Time time = timeService.create(requestTime);
        ResponseTime responseTime = ResponseTime.from(time);
        URI location = URI.create("/times/" + responseTime.id());
        return ResponseEntity.created(location).body(responseTime);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTime>> getAllTime() {
        List<ResponseTime> responseTimes = timeService.findAll().stream()
                .map(ResponseTime::from)
                .toList();
        return ResponseEntity.ok(responseTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(@PathVariable Long id) {
        timeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
