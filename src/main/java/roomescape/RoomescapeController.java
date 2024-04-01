package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RoomescapeController {
    private List<Reservation> reservations = new ArrayList<>();

    AtomicInteger atomic = new AtomicInteger(0);

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        if(reservation.getName().isEmpty() || reservation.getDate().isEmpty() || reservation.getDate().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Reservation newReservation = new Reservation(atomic.incrementAndGet(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable() int id) {
        if(reservations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reservation deletedReservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(deletedReservation);

        return null;
    }
}