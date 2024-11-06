package roomescape.controller;

import exception.NotFoundReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> params) {

        Reservation reservation = new Reservation(params.get("name"), params.get("date"), params.get("time"));

        if (reservation.getName() == null || reservation.getName().isEmpty() ||
                reservation.getDate() == null || reservation.getDate().isEmpty() ||
                reservation.getTime() == null || reservation.getTime().isEmpty()) {
            throw new IllegalArgumentException("예약 내용에 누락된 부분이 있습니다.");
        }

        long id = index.getAndIncrement();
        reservation.setId(id);
        reservations.add(reservation);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservation.getId())
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean isRemoved = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if (!isRemoved) {
            throw new NotFoundReservationException("삭제하려는 예약이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
