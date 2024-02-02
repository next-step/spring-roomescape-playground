package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.CreateTimeDto;
import roomescape.controller.dto.TimeDto;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.TimeDao;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private final TimeDao timeDao;

    TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDto> addTime(@Validated @RequestBody CreateTimeDto dto) {
        TimeDto savedTime = TimeDto.toDto(timeDao.save(dto));
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId())).body(savedTime);
    }

    @GetMapping("times")
    public ResponseEntity<List<TimeDto>> timeList() {
        List<TimeDto> times = timeDao.findAllTimes()
                .stream()
                .map(TimeDto::toDto)
                .toList();

        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity removeTime(@PathVariable("id") Long id) {
        timeDao.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity handleTimeException(NotFoundTimeException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
