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
        reservations.add(new Reservation(1, "브라운", "2025-06-16", "14:00"));
        reservations.add(new Reservation(2, "브라운", "2025-06-15", "15:00"));
        reservations.add(new Reservation(3, "브라운", "2025-06-14", "16:00"));
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping("/reservations")
    // 컨트롤러에서 200을 반환하지만 프론트에서는 201을 인식한다
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
