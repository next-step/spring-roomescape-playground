package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.CreateTimeRequestDto;
import roomescape.dto.CreateTimeResponseDto;
import roomescape.dto.ReadTimeDto;
import roomescape.service.TimeService;

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
    public ResponseEntity<List<ReadTimeDto>> readTimes() {
        List<ReadTimeDto> timeDtos = timeService.findAllTimes();
        return ResponseEntity.ok().body(timeDtos);
    }

    @PostMapping
    public ResponseEntity<CreateTimeResponseDto> createTime(@Valid @RequestBody CreateTimeRequestDto timeDTO) {
        CreateTimeResponseDto createTimeResponseDto = timeService.createTime(timeDTO);
        return ResponseEntity.created(URI.create("/times/" + createTimeResponseDto.getTimeId())).body(createTimeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
