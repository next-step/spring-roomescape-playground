package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservationCheck() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> reservationAdd(@RequestBody Reservation reservation) {
        Reservation reservation2 = reservationService.addReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation2.id())).body(reservation2);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> reservationDelete(@PathVariable int id) {
        reservationService.removeReservation(id);
        return ResponseEntity.noContent().build();
    }

}
