package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.SaveReservationRequest;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String gotoReservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations(){
        final var reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> makeReservation(@RequestBody SaveReservationRequest request) {
        Reservation reservation = request.toReservation();
        int id = reservationRepository.save(reservation);
        ReservationResponse response = ReservationResponse.from(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id) {
        int affectRow = reservationRepository.deleteById(id);
        if(affectRow == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}
