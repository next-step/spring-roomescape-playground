package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.dto.AddReservationResponse;
import roomescape.domain.dto.CheckReservationsResponse;
import roomescape.domain.dto.RequestReservation;
import roomescape.repository.JdbcReservationRepository;

import java.net.URI;
import java.util.List;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final JdbcReservationRepository reservationRepository;

    public ReservationController(JdbcReservationRepository repository) {
        this.reservationRepository = repository;
    }

    @GetMapping
    public ResponseEntity<List<CheckReservationsResponse>> checkReservations() {
        List<CheckReservationsResponse> reservations = reservationRepository.findAll();

        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<AddReservationResponse> addReservation(@RequestBody RequestReservation request) {
        Reservation reservation = reservationRepository.addReservation(request);

        URI location = URI.create("/reservations/" + reservation.getId());
        AddReservationResponse responseReservation = AddReservationResponse.from(reservation);

        return ResponseEntity.created(location).body(responseReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteReservationById(id);

        return ResponseEntity.noContent().build();
    }
}
