package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.ReservationRepository;
import roomescape.domain.exception.NotFoundReservationException;
import roomescape.dto.Reservation;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        final List<Reservation> reservations = reservationRepository.findReservation();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = reservationRepository.insertReservation(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

//    @DeleteMapping("/reservations/{id}")
//    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
//        Reservation reservation = reservations.stream()
//                .filter(it -> Objects.equals(it.getId(), id))
//                .findFirst()
//                .orElseThrow(() -> new NotFoundReservationException("삭제할 ID" + id + "가 존재하지 않습니다."));
//
//        reservations.remove(reservation);
//
//        return ResponseEntity.noContent().build();
//    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}