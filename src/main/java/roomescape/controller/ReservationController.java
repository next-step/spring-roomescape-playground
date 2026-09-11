package roomescape.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationController(
            ReservationRepository reservationRepository,
            TimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {

        List<Reservation> reservations = reservationRepository.getReservations();

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> postReservation(
            @RequestBody ReservationRequest reservationRequest
    ) {

        if (reservationRequest.getName() == null
                || reservationRequest.getName().trim().isBlank()
                || reservationRequest.getDate() == null
                || reservationRequest.getTime() == null) {
            throw new IllegalArgumentException();
        }

        Time time = timeRepository.getTime(reservationRequest.getTime())
                .orElseThrow(NoSuchElementException::new);

        Reservation reservation = reservationRepository.saveReservation(
                reservationRequest.getName(),
                reservationRequest.getDate(),
                time
        );

        return ResponseEntity.created(
                        URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable long id) {

        Reservation reservation = reservationRepository.getReservation(id)
                .orElseThrow(NoSuchElementException::new);

        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {

        int deletedCount = reservationRepository.deleteReservation(id);

        if (deletedCount == 0) {
            throw new NoSuchElementException();
        }

        return ResponseEntity.noContent().build();
    }
}
