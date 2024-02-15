package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations(){
//        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2022-08-05", "15:40"));
//        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2022-08-05", "18:40"));
//        reservations.add(new Reservation(index.incrementAndGet(), "브라운", "2022-08-05", "20:40"));
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> setReservations(@RequestBody Reservation reservation){
        Reservation newReservation = Reservation.builder()
                .id(index.incrementAndGet())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id){
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
