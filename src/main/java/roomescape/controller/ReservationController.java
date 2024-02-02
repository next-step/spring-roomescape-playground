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
import roomescape.domain.Reservation;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservationService.findAllReservation();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        Long reservationId = reservationService.saveReservation(reservationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservationId)
                .body(reservationService.findReservationById(reservationId));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservationById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
