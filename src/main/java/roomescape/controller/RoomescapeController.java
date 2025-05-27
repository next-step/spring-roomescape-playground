package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Controller
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index;

    public RoomescapeController() {
//        reservations.add(new Reservation(1L, "오찌", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));
//        reservations.add(new Reservation(2L, "장순", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));
//        reservations.add(new Reservation(3L, "희정", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));
//        reservations.add(new Reservation(4L, "예진", LocalDate.of(2025, 6, 2), LocalTime.of(17, 0)));

        long maxId = reservations.stream()
                .mapToLong(Reservation::getId)
                .max()
                .orElse(0L);
        this.index = new AtomicLong(maxId);
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> reservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(
            @RequestBody ReservationRequest reservationRequest
    ) {
        Long newId = index.incrementAndGet();
        Reservation newReservation = new Reservation(
                newId,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());

        reservations.add(newReservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + newId))
                .body(ReservationResponse.from(newReservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Object> cancelReservation(
            @PathVariable Long id
    ) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}
