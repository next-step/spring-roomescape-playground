package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.InvalidReservationException;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/reservations")
public class ReservationAPIController {
    private final ReservationRepository reservationRepository;

    public ReservationAPIController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    @GetMapping
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> params) {
        String name=params.get("name");
        String date=params.get("date");
        String time=params.get("time");

        if (name == null || name.isBlank() || date == null || date.isBlank() || time == null || time.isBlank()) {
            throw new InvalidReservationException("name, date, time 모두 필요합니다.");
        }

        Reservation reservation = new Reservation(null, name, date, time);
        Reservation saved= reservationRepository.save(reservation);

        return ResponseEntity
            .created( URI.create("/reservations/" + reservation.getId()))
            .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean deleted = reservationRepository.deleteById(id);

        if (!deleted) {
            throw new InvalidReservationException("삭제 중 오류 발생");
        }

        return ResponseEntity.noContent().build();
    }
}
