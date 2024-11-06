package roomescape.controller;

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
    private AtomicLong index = new AtomicLong(0);

    // 예약 화면 보여주rl
    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    // 예약 조회
    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    // 에약 추가
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> params) {

        Reservation reservation = new Reservation(params.get("name"), params.get("date"), params.get("time"));

        long id = index.getAndIncrement();
        reservation.setId(id);
        reservations.add(reservation);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location" + "/reservations/" + reservation.getId())
                .body(reservation);
    }

    // 예약 삭제
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        boolean isRemoved = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if(!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

}
