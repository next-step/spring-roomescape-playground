package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<List<Time>> showTimeList() {
        return ResponseEntity.ok(timeService.getAllTime());
    }

    @PostMapping
    public ResponseEntity<Time> addTime(@RequestBody Time time) {
        Time newTime =  timeService.addTime(time);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
