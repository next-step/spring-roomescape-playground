package hello.controller;

import hello.controller.dto.CreateTimeDto;
import hello.controller.dto.TimeDto;
import hello.repository.TimeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("times")
    public ResponseEntity<List<TimeDto>> timeList() {

        List<TimeDto> Times = timeRepository.findAllTimes()
                .stream()
                .map(TimeDto::toDto)
                .toList();

        return ResponseEntity.ok(Times);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDto> addTime(@Validated @RequestBody CreateTimeDto dto) {

        TimeDto savedTime = TimeDto.toDto(timeRepository.save(dto));
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId())).body(savedTime);
    }
}
