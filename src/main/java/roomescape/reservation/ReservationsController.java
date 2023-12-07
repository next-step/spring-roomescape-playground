package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationService reservationService;
    public ReservationsController( ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> select (){
        return ResponseEntity.ok().body(reservationService.getReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> create ( @RequestBody ReservationRequest reservationRequest ){
        Reservation newReservation = reservationService.postReservations(reservationRequest);
        int id = (int)newReservation.getId();
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        reservationService.deleteReservations(id);
        return  ResponseEntity.noContent().build();
    }

}
