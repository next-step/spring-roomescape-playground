package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
public class ReservationApiController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime()
        );
        reservations.add(reservation);

        ReservationResponse reservationResponse = new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.getId()))
                .body(reservationResponse);
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> getReservations() {
        return reservations.stream().map(r -> new ReservationResponse(r.getId(), r.getName(), r.getDate(), r.getTime()))
                .toList();
    }
}
