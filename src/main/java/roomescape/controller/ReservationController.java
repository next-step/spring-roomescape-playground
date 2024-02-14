package roomescape.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ReservationController {
    private AtomicInteger index = new AtomicInteger(1);
    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }
    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationService.getAllReservations();
    }
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@Validated @RequestBody Reservation reservation) {
        Reservation newreservation = new Reservation(index.getAndIncrement(),reservation.getName(), reservation.getDate(),reservation.getTime());
        reservationService.saveReservation(newreservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}