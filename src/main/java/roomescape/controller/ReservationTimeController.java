package roomescape.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Controller
public class ReservationTimeController {

    private final ReservationTimeRepository repository;

    public ReservationTimeController(final ReservationTimeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/time")
    public String getReservationTime() {
        return "time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> saveReservationTime(@RequestBody ReservationTimeRequest request) {
        final ReservationTime savedTime = repository.save(request);
        final ReservationTimeResponse response = ReservationTimeResponse.from(savedTime);
        return ResponseEntity
                .created(URI.create("/times/" + response.id()))
                .body(response);
    }

    @GetMapping("/times")
    public void getReservationTimes() {

    }

    @DeleteMapping("/times")
    public void deleteReservationTimes() {

    }

}
