package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations(){
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> getReservation(@PathVariable long id){
        Reservation reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        Reservation newReservation = reservationService.createReservation(reservation);
        String uri = "/reservations/" +  newReservation.getId();
        return ResponseEntity.created(URI.create(uri)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id){
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
