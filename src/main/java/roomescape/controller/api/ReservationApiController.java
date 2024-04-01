package roomescape.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

@Controller
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    @GetMapping
    @ResponseBody
    public List<Reservation> reservations() {
        return reservationService.reservations();
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody Reservation request) {
        Reservation reservation = reservationService.addReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
