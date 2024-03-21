package roomescape.domain.time.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.time.dto.CreateTimeRequestDto;
import roomescape.domain.time.dto.TimeDto;
import roomescape.domain.time.service.TimeService;
import roomescape.exception.NotFoundTimeException;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<TimeDto> addTime(@RequestBody @Valid CreateTimeRequestDto requestDto) {
        TimeDto time = timeService.saveTime(requestDto);
        return ResponseEntity.created(URI.create("/times/" + time.getId())).body(time);
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> timeList() {
        List<TimeDto> times = timeService.getTimes();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{timeId}")
    public ResponseEntity removeTime(@PathVariable("timeId") Long timeId) {
        timeService.removeTime(timeId);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity handleTimeException(NotFoundTimeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
