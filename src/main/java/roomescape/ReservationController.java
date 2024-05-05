package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong atomicLong = new AtomicLong(0);

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody CreateReservationRequestDto request) {
        Reservation reservation = new Reservation(atomicLong.incrementAndGet(), request.getName(), request.getDate(), request.getTime());
        reservations.add(reservation);
        return ResponseEntity.status(201).location(URI.create("/reservations/"+reservation.getId())).body(reservation);
    }

@DeleteMapping("/reservations/{id}")
public ResponseEntity<Void> create(@PathVariable int id) {
    Reservation reservation1 = reservations.stream()
            .filter(reservation -> reservation.getId() == id)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    reservations.remove(reservation1);
    return ResponseEntity.status(204).build();
}

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Void> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
}



    @GetMapping("/reservation")
    public String reservation () {
        return "home";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {

        // 응답 생성 및 반환
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }
}