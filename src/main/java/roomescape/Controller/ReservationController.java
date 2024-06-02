package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Model.RequestReservationDTO;
import roomescape.Model.Reservation;
import roomescape.Model.ReservationService;
import java.net.URI;
import java.util.*;

@Controller
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "new-reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations(){
        return ResponseEntity.ok(reservationService.getReservationsList());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody RequestReservationDTO reservation){
        Reservation savedReservation= reservationService.saveReservation(reservation);
        URI location=URI.create("/reservations/"+savedReservation.getId());
        return ResponseEntity.created(location).body(savedReservation);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> reservationResult(@PathVariable Long id){
        Reservation findReservation=reservationService.viewReservation(id);
        return ResponseEntity.ok(findReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}