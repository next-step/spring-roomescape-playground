package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private static final AtomicLong idCounter = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();


    @GetMapping
    public ResponseEntity<List<Reservation>> getReservation(){
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        Reservation newReservation = new Reservation();
        newReservation.setId(idCounter.incrementAndGet());
        newReservation.setName(reservation.getName());
        newReservation.setDate(reservation.getDate());
        newReservation.setTime(reservation.getTime());

        reservations.add(newReservation);

        return ResponseEntity
                .status(201)
                .location(java.net.URI.create("/reservations/"+newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) throws Exception {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                reservations.remove(reservation);
                idCounter.decrementAndGet();
                return ResponseEntity.noContent().build();
            }
        }
        throw new Exception("ID(" + id + ")가 없습니다");
    }


}
