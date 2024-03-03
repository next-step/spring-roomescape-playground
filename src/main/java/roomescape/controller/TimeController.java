package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeResponseDTO;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeResponseDTO> saveTime(@RequestBody Time time) {
        Long id = timeService.saveTime(time);
        TimeResponseDTO newTime = new TimeResponseDTO(id, time.getTime());

        return ResponseEntity.created(URI.create("/times/" + id)).body(newTime);
    }

    @GetMapping
    public ResponseEntity<List<Time>> getAllTime() {

        return ResponseEntity.ok(timeService.findAllTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}
