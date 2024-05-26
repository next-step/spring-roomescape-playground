package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.dto.TimeResponseDto;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
public class TimeController {
    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponseDto>> getReservations() {
        List<TimeResponseDto> times = timeService.getTimes();
        return ResponseEntity.ok(times);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponseDto> createTime(@RequestBody TimeRequestDto requestDto) {
        TimeResponseDto createdTime = timeService.createTime(requestDto);
        return ResponseEntity.created(URI.create("/times/" + createdTime.id())).body(createdTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

}
