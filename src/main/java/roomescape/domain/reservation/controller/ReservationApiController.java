package roomescape.domain.reservation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.service.ReservationService;
import roomescape.domain.reservation.service.ReservationServiceImpl;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    ReservationService reservationService = new ReservationServiceImpl();

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody @Valid Reservation reservation) {
        Reservation newReservation = reservationService.addReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> reservations(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservationService.getAllReservation());
    }
}
