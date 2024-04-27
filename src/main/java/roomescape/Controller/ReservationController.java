package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationDTO;
import roomescape.Domain.Reservation;
import roomescape.Service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservation() {
        return reservationService.allReservation();
    }

    @PostMapping
    public ResponseEntity<Reservation> createdReservation(@RequestBody ReservationDTO reservation) {
        Reservation newReservation = reservationService.createdReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletedReservation(@PathVariable Long id) {
        reservationService.deletedReservation(id);
        return ResponseEntity.noContent().build();
    }
}
