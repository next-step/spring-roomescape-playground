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

import roomescape.dto.TimeAddRequest;
import roomescape.dto.TimeResponse;
import roomescape.service.ReservationService;

@Controller
public class TimeController {
    private final ReservationService reservationService;

    public TimeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> timeList() {
        final List<TimeResponse> times = reservationService.findTimeList();

        return ResponseEntity.ok().body(times);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> timeSave(@RequestBody TimeAddRequest request) {
        final TimeResponse time = reservationService.addTime(request);

        return ResponseEntity.created(URI.create("/times/" + time.getId())).body(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> timeRemove(@PathVariable Long id) {
        reservationService.removeTime(id);

        return ResponseEntity.noContent().build();
    }
}
