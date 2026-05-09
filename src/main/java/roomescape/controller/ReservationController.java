package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationGetResponse;
import roomescape.exception.NotFoundReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationGetResponse> getReservations() {
        return reservations.stream()
                .map(it -> ReservationGetResponse.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .date(it.getDate())
                        .time(it.getTime())
                        .build())
                .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> add(@RequestBody @Valid ReservationCreateRequest reservationCreateRequest) {
        Long newId = index.getAndIncrement();
        Reservation newReservation = reservationCreateRequest.toEntity(newId);

        reservations.add(newReservation);

        ReservationCreateResponse response = ReservationCreateResponse.from(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + response.getId())).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation removeTarget = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(removeTarget);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NotFoundReservationException.class})
    public ResponseEntity<Void> handleException() {
        return ResponseEntity.badRequest().build();
    }
}