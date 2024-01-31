package hello.controller;

import hello.controller.dto.CreateTimeDto;
import hello.controller.dto.TimeDto;
import hello.repository.TimeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> timeList() {

        List<TimeDto> Times = timeRepository.findAllTimes()
                .stream()
                .map(TimeDto::toDto)
                .toList();

        return ResponseEntity.ok(Times);
    }

    @PostMapping
    public ResponseEntity<TimeDto> addTime(@Validated @RequestBody CreateTimeDto dto) {

        TimeDto savedTime = TimeDto.toDto(timeRepository.save(dto));
        return ResponseEntity.created(URI.create("/times/" + savedTime.getId())).body(savedTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTime(@PathVariable("id") Long id) {

        timeRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
