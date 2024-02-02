package roomescape.domain.time.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.time.dto.request.TimeCreateRequestDto;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.service.TimeService;

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
    public ResponseEntity<Time> createTime(@RequestBody @Valid TimeCreateRequestDto requestDto) {
        Time time = timeService.saveTime(requestDto);
        return ResponseEntity.created(URI.create("/times/" + time.getId())).body(time);
    }

    @GetMapping
    public ResponseEntity<List<Time>> readTime() {
        List<Time> times = timeService.getTimes();
        return ResponseEntity.ok().body(times);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long timeId) {
        timeService.deleteTime(timeId);
        return ResponseEntity.noContent().build();
    }
}