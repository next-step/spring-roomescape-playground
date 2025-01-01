package roomescape.timeManage;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.timeManage.dto.AddTimeRequest;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeApiController {
    private final TimeService timeService;

    public TimeApiController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeService.getAllTimes();
        return ResponseEntity.ok().body(times);
    }

    @PostMapping
    public ResponseEntity<Time> addTime(@RequestBody @Valid AddTimeRequest timeRequest) {
        String time = timeRequest.getTime();
        Time timeSchedule = timeService.addTime(time);
        URI location = URI.create("/times/" + timeSchedule.getId());
        return ResponseEntity.created(location).body(timeSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTime(@PathVariable Long id) {
        timeService.cancelTime(id);
        return ResponseEntity.noContent().build();
    }
}
