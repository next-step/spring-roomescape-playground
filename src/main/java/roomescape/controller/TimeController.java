package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReadTimeDto;
import roomescape.dto.CreateTimeRequestDto;
import roomescape.dto.CreateTimeResponseDto;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public ResponseEntity<List<ReadTimeDto>> readTimes() {
        List<ReadTimeDto> timeDtos = timeService.findAllTimes();
        return ResponseEntity.ok().body(timeDtos);
    }

    @PostMapping
    public ResponseEntity<CreateTimeResponseDto> createTime(@RequestBody CreateTimeRequestDto timeDTO) {
        CreateTimeResponseDto createTimeResponseDto = timeService.createTime(timeDTO);
        return ResponseEntity.created(URI.create("/times/" + createTimeResponseDto.getTimeId())).body(createTimeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
