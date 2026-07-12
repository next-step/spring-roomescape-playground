package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.model.time.Time;
import roomescape.model.time.TimeDTO;
import roomescape.model.time.TimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    @ResponseBody
    public List<Time> getTimes() {
        return timeService.getAllTimes();
    }

    @PostMapping
    public ResponseEntity<Time> addTime(@RequestBody TimeDTO timeDTO) {
        Time time = timeService.addTime(timeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/times/" + time.getId())
                .body(time);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
