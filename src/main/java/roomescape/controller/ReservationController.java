package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.dto.RequestReservation;
import roomescape.domain.dto.ResponseReservation;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;

@RequestMapping("/reservations")
@Controller
public class ReservationController {

    @Autowired
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository repository) {
        this.reservationRepository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> checkReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ResponseReservation> addReservation(@RequestBody RequestReservation request) {

        Reservation newReservation = request.toReservation();
        reservationRepository.addReservation(newReservation);

        URI location = URI.create("/reservations/" + newReservation.getId());
        ResponseReservation responseReservation = ResponseReservation.from(newReservation);

        return ResponseEntity.created(location).body(responseReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        reservationRepository.deleteReservationById(id);

        return ResponseEntity.noContent().build();
    }
}
