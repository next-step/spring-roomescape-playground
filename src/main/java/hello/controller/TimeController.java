package hello.controller;

import hello.controller.dto.CreateTimeDto;
import hello.service.TimeService;
import hello.service.dto.TimeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> timeList() {
        List<TimeDto> times = timeService.getTimeList();
        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<TimeDto> addTime(@Validated @RequestBody CreateTimeDto dto) {
        TimeDto savedTime = timeService.save(dto);
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId())).body(savedTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTime(@PathVariable("id") Long id) {
        timeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}