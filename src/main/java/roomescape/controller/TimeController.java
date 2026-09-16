package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.TimeRequestDto;
import roomescape.controller.dto.TimeResponseDto;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponseDto>> read() {
        return ResponseEntity.ok().body(timeService.read());
    }


    @PostMapping("/times")
    public ResponseEntity<TimeResponseDto> create(@RequestBody TimeRequestDto timeRequestDto) {
        TimeResponseDto timeResponseDto = timeService.create(timeRequestDto);
        return ResponseEntity.created(URI.create("/times/"+timeResponseDto.id())).body(timeResponseDto);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
