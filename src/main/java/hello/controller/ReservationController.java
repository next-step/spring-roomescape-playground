package hello.controller;

import hello.domain.Reservation;
import hello.exceptions.NotFoundReservationException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservation")
    public String reservation() {
        return "/reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> reservationList() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@Validated @RequestBody createReservationDto dto) {

        Reservation newReservation = Reservation.create(index.incrementAndGet(), dto.getName(), dto.getDate(), dto.getTime());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    static class createReservationDto {

        @NotEmpty
        private String name;
        @NotNull
        private LocalDate date;
        @NotNull
        private LocalTime time;

        public String getName() {
            return name;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalTime getTime() {
            return time;
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") Long id) {

        Reservation reservation = reservations.stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
