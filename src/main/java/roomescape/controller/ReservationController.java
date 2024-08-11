package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "reservation";
    }

    private boolean isValidReservation(Reservation reservation) {
        return reservation.getName() != null && !reservation.getName().isEmpty() &&
                reservation.getDate() != null && !reservation.getDate().isEmpty() &&
                reservation.getTime() != null && !reservation.getTime().isEmpty();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
       if (!isValidReservation(reservation)) {
           return ResponseEntity.badRequest().build();
       }

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("Reservation not found for ID: " + id));

        reservations.remove(reservation);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        return ResponseEntity.ok().body(reservations);
    }
}
