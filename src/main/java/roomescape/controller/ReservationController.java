package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.model.Reservation;
import roomescape.model.ReservationRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    // 예약 관리 페이지
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

    // 예약 추가
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest request) {
        request.validate();
        Reservation newReservation = new Reservation(null, request.getName(), request.getDate(), request.getTime());

        long id = index.getAndIncrement();
        newReservation.setId(id);
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    // 예약 삭제
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation reservationToDelete = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("삭제할 예약이 없습니다."));

        reservations.remove(reservationToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
