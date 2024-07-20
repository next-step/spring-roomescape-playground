package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public List<TimeResponseDto> getTimes() {
        return timeService.getAllTimes();
    }

    @PostMapping
    public ResponseEntity<TimeResponseDto> createTime(@RequestBody TimeRequestDto timeRequestDto) {
        TimeResponseDto newTimeDto = timeService.createTime(timeRequestDto);
        return ResponseEntity.created(URI.create("/times/" + newTimeDto.getId())).body(newTimeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
