package roomescape.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Exception.NotFindReservationException;
import roomescape.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger index = new AtomicInteger(1);

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
        if (reservation.getName()==null || reservation.getDate()==null||reservation.getTime()==null){
            return ResponseEntity.badRequest().build();
        }
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).build();
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> allReservations = new ArrayList<>(reservations);
        return ResponseEntity.ok(allReservations);
    }
    @PutMapping("/reservations/{id}")
    public ResponseEntity<List<Reservation>> update(@RequestBody Reservation newReservation, @PathVariable int id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservation.update(newReservation);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<List<Reservation>> delete(@PathVariable int id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFindReservationException::new);

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
