package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@Controller
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String getReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    /*@PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationDTO reservationDTO) {

        Long newReservation = reservationService.addReservation(reservationDTO);
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("id") Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("예약이 존재하지 않습니다!"));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }*/
}
