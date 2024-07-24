package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.TimeResponseDto;
import roomescape.dto.TimeSaveRequestDto;
import roomescape.service.TimeService;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public String goReservationPage() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponseDto> createTime(@RequestBody TimeSaveRequestDto requestDto) {
        TimeResponseDto timeResponseDto = timeService.createTime(requestDto);

        return ResponseEntity.created(URI.create("/times/" + timeResponseDto.getId()))
                .body(timeResponseDto);
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponseDto>> readAllTimes() {

        return ResponseEntity.ok().body(timeService.readAllTimes());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);

        return ResponseEntity.noContent().build();
    }
}
