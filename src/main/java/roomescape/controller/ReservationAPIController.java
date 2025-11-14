package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.InvalidReservationException;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/reservations")
public class ReservationAPIController {
    private final List<Reservation> reservations=new ArrayList<>();
    private final AtomicLong idCounter=new AtomicLong(1);

    @GetMapping
    public List<Reservation> getReservations() {
        return reservations;
    }
    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> params) {
        Reservation reservation = new Reservation(
            idCounter.getAndIncrement(),
            params.get("name"),
            params.get("date"),
            params.get("time")
        );
        String name=params.get("name");
        String date=params.get("date");
        String time=params.get("time");

        if (name == null || name.isBlank() || date == null || date.isBlank() || time == null || time.isBlank()) {
            throw new InvalidReservationException("name, date, time 모두 필요합니다.");
        }
        reservations.add(reservation);
        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", "/reservations/" + reservation.getId())
            .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed= reservations.removeIf(r -> r.getId().equals(id));
        if (!removed) {
            throw new InvalidReservationException("삭제할 예약이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
