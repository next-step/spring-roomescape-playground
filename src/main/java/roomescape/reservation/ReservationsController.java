package roomescape.reservation;

import error.Exception400;
//import error.HandleException;
import org.springframework.http.ResponseEntity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationService reservationService;
    public ReservationsController( ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> GetReservations (){
        return ResponseEntity.ok().body(reservationService.getReservations());
    }


    @PostMapping
    public ResponseEntity<Reservation> PostReservations ( @RequestBody Reservation reservation ){
        if (reservation.getName().isEmpty() ||reservation.getDate().isEmpty() || reservation.getTime().isEmpty()) {
            throw new Exception400("Date and time cannot be null");
        }
        Reservation newReservation = reservationService.postReservations(reservation);
        int id = (int)newReservation.getId();
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteReservations (@PathVariable Long id){
        reservationService.deleteReservations(id);
        return  ResponseEntity.noContent().build();
    }

}
