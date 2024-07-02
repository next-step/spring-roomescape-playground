package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(1);
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() { return "reservation"; }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        if (request.getName() == null || request.getDate() == null || request.getTime() == null) {
            return ResponseEntity.badRequest().build();
        }

        Long newId = index.getAndIncrement();
        Reservation newReservation = new Reservation(newId, request.getName(), request.getDate(), request.getTime());
        reservations.add(newReservation);
        ReservationResponse response = new ReservationResponse(newReservation);

        URI location = URI.create(String.format("/reservations/%d", newId));
        return ResponseEntity.created(location).body(response);
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
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }
}
