package roomescape.controller;

import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidRequestException;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        return ResponseEntity.ok(reservations);
    }


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation request) {
        if (StringUtils.isBlank(request.getName()) || StringUtils.isBlank(request.getDate()) || StringUtils.isBlank(request.getTime())) {
            throw new InvalidRequestException("예약 정보에 공백이 입력되었습니다.");
        }

        long id = index.incrementAndGet();
        Reservation reservation = new Reservation(id, request.getName(), request.getDate(), request.getTime());
        reservations.add(reservation);

        URI location = URI.create("/reservations/" + id);

        return ResponseEntity.created(location).body(reservation);
    }



    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("예약을 찾을 수 없습니다."));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }


}
