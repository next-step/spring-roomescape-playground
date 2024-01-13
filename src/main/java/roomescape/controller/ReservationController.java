package roomescape.controller;
import roomescape.Entity.Reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Exception.BadRequestException;
import roomescape.Exception.NotFoundReservationException;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
        private List<Reservation> reservations = new ArrayList<>();
        private AtomicLong index = new AtomicLong(1);

        @GetMapping("/reservation")
        public String Reservation() {
            return "reservation";
        }

        @GetMapping("/reservations")
        public ResponseEntity<List<Reservation>> readReservation() {
            return ResponseEntity.ok().body(reservations);
        }

        @PostMapping("/reservations")
        public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
            if (reservation.getName() == "" || reservation.getDate() == "" || reservation.getTime() == "") {
                throw new BadRequestException("Name, date, and time are required for reservation creation");
            }
            Reservation newReservation = Reservation.toEntity(index.getAndIncrement(), reservation);
            reservations.add(newReservation);
            URI location = URI.create("/reservations/" + newReservation.getId());
            return ResponseEntity.created(location).body(newReservation);
        }

        @PutMapping("/reservations/{id}")
        public ResponseEntity<Void> updateReservation(@RequestBody Reservation newReservation, @PathVariable Long id) {
            Reservation member = reservations.stream()
                    .filter(it -> Objects.equals(it.getId(), id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));

            member.update(newReservation);
            return ResponseEntity.ok().build();
        }

        @DeleteMapping("/reservations/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
            Reservation reservation = reservations.stream()
                    .filter(it -> Objects.equals(it.getId(), id))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));

            reservations.remove(reservation);

            return ResponseEntity.noContent().build();
        }

}
