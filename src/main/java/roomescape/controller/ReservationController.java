package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDTO;
import roomescape.dto.ReservationResponseDTO;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> reservations = reservationService.findAllReservations();

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDTO> makeReservation(@RequestBody ReservationDTO reservationDTO) {

        ReservationResponseDTO newReservation = reservationService.makeReservation(reservationDTO);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.id())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
