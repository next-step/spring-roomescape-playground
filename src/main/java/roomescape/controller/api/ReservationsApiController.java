package roomescape.controller.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import roomescape.domain.Reservation;

@Controller
@RequestMapping("reservations")
public class ReservationsApiController {

    private AtomicLong index = new AtomicLong(0);
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    @ResponseBody
    public List<Reservation> reservations(){
        return reservations;
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation request) {
        Reservation reservation = new Reservation(
            index.incrementAndGet(),
            request.getName(),
            request.getDate(),
            request.getTime()
        );
        reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
            .body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> it.getId().equals(id))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
