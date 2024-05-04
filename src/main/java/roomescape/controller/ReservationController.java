package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReadReservationResponse;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static java.lang.String.valueOf;


@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);


    @GetMapping("/reservation")
    public String reservation() {
        return "reservation.html";
    }


    // 예약 목록 조회 API
    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReadReservationResponse>> readAll() {
        final List<ReadReservationResponse> responses = reservations.stream()
                .map(ReadReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    //예약 추가


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create (@RequestBody Reservation request) {
        long id = index.incrementAndGet();

        Reservation newReservation = new Reservation(id, request.getName(), request.getDate(), request.getTime());
        reservations.add(newReservation);

        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).body(newReservation);
    }

    // 예약 삭제

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        if (reservations.stream().noneMatch(reservation -> reservation.getId().equals(id))) {
            return ResponseEntity.notFound().build();
        }

        for (Reservation reservation : this.reservations) {
            if (reservation.getId().equals(id)) { // class 비교할 때는 equals 메서드를 씀
                this.reservations.remove(reservation);
                break;
            }
        }
        return ResponseEntity.noContent().build();
    }


}