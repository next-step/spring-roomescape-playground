package roomescape.domain.reservationTime.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservationTime.dto.ReservationTimeRequest;
import roomescape.domain.reservationTime.dto.ReservationTimeResponse;
import roomescape.domain.reservationTime.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(
            @RequestBody @Valid final ReservationTimeRequest timeRequest) {
        ReservationTimeResponse result = reservationTimeService.createReservationTime(timeRequest);
        URI Location = URI.create("/times/" + result.id());

        return ResponseEntity.created(Location).body(result);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
        List<ReservationTimeResponse> result = reservationTimeService.getReservationTimes();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity deleteReservationTime(@PathVariable final Long reservationTimeId) {
        reservationTimeService.deleteReservationTime(reservationTimeId);

        return ResponseEntity.noContent().build();
    }
}
