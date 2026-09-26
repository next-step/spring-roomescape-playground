package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong id = new AtomicLong(1);


    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {

        Reservation newReservation = new Reservation(
                id.getAndIncrement(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());

        reservations.add(newReservation);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location","/reservations/"+newReservation.getId()).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        Reservation asdf = reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
        reservations.remove(asdf);
        return ResponseEntity.noContent().build();
    }

//    테스트용 데이터 추가
//    public ReservationController() {
//        reservations.add(new Reservation(1L,"가나다", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//        reservations.add(new Reservation(2L,"마바사", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//        reservations.add(new Reservation(3L,"abc", LocalDate.of(2026,1,1), LocalTime.of(10,0)));
//    }
}
