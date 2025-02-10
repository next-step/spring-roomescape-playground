package roomescape.controller;

import jakarta.transaction.Transactional;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.entity.repository.ReservationRepository;
import roomescape.exception.NotFoundException;
import roomescape.exception.ReservationException;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        final Reservation save = reservationRepository.save(reservation);
        URI location = URI.create("/reservations/" + save.getId());
        return ResponseEntity.created(location).body(save);
    }

    @Transactional
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        final int countOfDeleted = reservationRepository.deleteById(id);

        if (countOfDeleted <= 0) {
            throw new NotFoundException("해당 id를 가진 예약을 찾을 수 없습니다.");
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> handleException(ReservationException e) {
        HttpStatus status = e.getHttpStatus();
        String message = e.getMessage();

        return ResponseEntity.status(status).body(message);
    }
}
