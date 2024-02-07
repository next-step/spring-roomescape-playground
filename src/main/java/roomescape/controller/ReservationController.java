package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.exception.ReservationErrorMessage;
import roomescape.exception.ReservationException;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);


    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(Reservation::toResponse)
                .toList();
        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
        Reservation newReservation = reservation.toEntity(index.incrementAndGet(), reservation);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation.toResponse());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ReservationException(ReservationErrorMessage.NOT_FOUND));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}