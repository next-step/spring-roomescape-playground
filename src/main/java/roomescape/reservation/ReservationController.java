package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(1);
    private List<ReservationResponse> reservations = new ArrayList<>(List.of());

    @GetMapping("/reservation")
    public String reservation() { return "reservation"; }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        if (request.getName() == null || request.getDate() == null || request.getTime() == null) {
            return ResponseEntity.badRequest().build();
        }

        Long newId = index.getAndIncrement();
        ReservationResponse newReservation = ReservationResponse.toEntity(newId, request);
        reservations.add(newReservation);

        URI location = URI.create(String.format("/reservations/%d", newId));
        return ResponseEntity.created(location).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<List<ReservationResponse>> deleteReservation(@PathVariable Long id) {
        boolean exists = reservations.stream().anyMatch(reservation -> reservation.getId().equals(id));
        if (!exists) {
            return ResponseEntity.badRequest().build();
        }

        reservations = reservations.stream()
                .filter(reservation -> !reservation.getId().equals(id))
                .collect(Collectors.toList());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }
}
