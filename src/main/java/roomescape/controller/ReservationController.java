package roomescape.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationAddRequest;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationAddResponse;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservation = reservationService.findAllReservations();
        return ResponseEntity.ok().body(reservation);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationAddResponse> create(final @Valid @RequestBody ReservationAddRequest reservationAddRequest) {
        Reservation reservation = reservationService.createReservation(reservationAddRequest);

        ReservationAddResponse reservationAddResponse = ReservationAddResponse.toDto(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .contentType(MediaType.APPLICATION_JSON).body(reservationAddResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(final @PathVariable @Positive Long id) {
        boolean exist = reservationService.deleteReservation(id);
        if (!exist) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
