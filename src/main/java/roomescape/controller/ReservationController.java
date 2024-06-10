package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations(){
        return ResponseEntity.ok(reservationRepository.getAllReservations());
    }

    @GetMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> getReservation(@PathVariable long id){
        return ResponseEntity.ok(reservationRepository.getReservationById(id));
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation newReservation, HttpServletRequest request) {
        long id = reservationRepository.saveReservation(newReservation);
        String uri = "/reservations/" + id;
        return ResponseEntity.created(URI.create(uri)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id){
        reservationRepository.deleteReservationById(id);
        return ResponseEntity.noContent().build();
    }
}
