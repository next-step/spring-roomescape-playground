package roomescape.reservation.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;

@Controller
public class ReservationController {


    private final ReservationService reservationService;

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
        reservations.add(new Reservation(index.incrementAndGet(), "비토", LocalDate.of(2023, 1, 1), LocalTime.of(10, 1)));
        reservations.add(new Reservation(index.incrementAndGet(), "곰곰", LocalDate.of(2023, 1, 2), LocalTime.of(10, 2)));
        reservations.add(new Reservation(index.incrementAndGet(), "망고", LocalDate.of(2023, 1, 3), LocalTime.of(10, 3)));
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservationService.getReservationResponses(reservations);

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Long newId = index.incrementAndGet();
        ReservationResponse result = reservationService.createReservation(reservationRequest, reservations,
                newId);

        URI Location = URI.create("/reservations/" + newId);

        return ResponseEntity.created(Location).body(result);
    }

    @DeleteMapping("/reservations/{reservationId}") // 경로 변수 올바르게 수정
    public ResponseEntity deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservations, reservationId);

        return ResponseEntity.noContent().build();
    }
}
