package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Time;
import roomescape.dto.time.TimeRequest;
import roomescape.service.ReservationService;

@Controller
public class TimeController {
    private final ReservationService reservationService;

    public TimeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/times")
    @ResponseBody
    public List<Time> getTimes() {
        return reservationService.findAllTime();
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@Valid @RequestBody TimeRequest timeRequest) {
        Long timeId = reservationService.saveTime(timeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/times/" + timeId)
                .body(reservationService.findTimeById(timeId));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationService.deleteTime(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
