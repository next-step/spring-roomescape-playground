package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.time.BadRequestTimeException;
import roomescape.model.dto.TimeDto;
import roomescape.model.entity.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.ok(this.timeService.findTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@Valid @RequestBody TimeDto timeDto) {
        Time time = this.timeService.join(timeDto);
        return ResponseEntity
                .created(URI.create("/times/" + time.getId()))
                .body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        if (this.timeService.remove(id) == 0)
            throw new BadRequestTimeException();
        return ResponseEntity.noContent().build();
    }
}
