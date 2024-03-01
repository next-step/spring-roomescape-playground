package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Domain.Reservation;
import roomescape.Repository.ReservationDAO;
import roomescape.Service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDAO)
    {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationDAO.findAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    /*
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id)
    {
        return reservationDAO.findReservation(id);
    }
    */

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Long id = reservationDAO.createReservation(reservation);
        Reservation newReservation = reservationDAO.findReservation(id);

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDAO.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
