package roomescape.reservation.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.exception.BadRequestException;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationApiController {
    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> read() {
        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        Reservation newReservation = Reservation.of(request, index.getAndIncrement());
        reservations.add(newReservation);

        URI location = URI.create("/reservations/" + newReservation.id());
        return ResponseEntity
                .created(location)
                .body(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(reservationItem -> Objects.equals(reservationItem.id(), id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("삭제할 예약이 존재하지 않습니다."));
        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }
}
