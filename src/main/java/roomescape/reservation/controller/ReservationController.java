package roomescape.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    @GetMapping("/reservation")
    public String getReservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.findReservations();
        return ResponseEntity.ok(reservations);
    }
}
