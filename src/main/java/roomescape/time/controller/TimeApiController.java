package roomescape.time.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.exception.BadRequestReservationException;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimeApiController {

    private final TimeService timeService;
    @GetMapping("/times")
    public ResponseEntity<?> times()  {
        List<TimeResponse> times = timeService.findAllTime();
        return ResponseEntity.ok(times);
    }

    @PostMapping("/times")
    public ResponseEntity<?> addTimes(@RequestBody TimeRequest timeRequest) {
        TimeResponse timeResponse = timeService.addTime(timeRequest);
        return ResponseEntity.created(URI.create("/times/" + timeResponse.getId())).body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<?> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

}
