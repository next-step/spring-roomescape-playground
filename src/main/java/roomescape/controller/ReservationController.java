package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong id = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservation) {
        Reservation newReservation = new Reservation(
                id.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());

        reservations.add(newReservation);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/reservations/" + newReservation.getId()).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation targetReservation = reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("예약을 찾을수 없습니다. id = " + id));
        reservations.remove(targetReservation);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handNotFound(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handIllgealArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

//    테스트용 데이터 추가
//    public ReservationController() {
//        reservations.add(new Reservation(1L,"가나다", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//        reservations.add(new Reservation(2L,"마바사", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//        reservations.add(new Reservation(3L,"abc", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//    }
}
