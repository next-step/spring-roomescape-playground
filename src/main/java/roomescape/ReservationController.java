package roomescape;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationController() {
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservationList() {
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        Reservation newReservation = request.toEntity(index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }


}
