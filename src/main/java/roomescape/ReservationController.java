package roomescape;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundException;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Void> handleInvalidReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/reservation")
    public String adminPage() {
        return "reservation";
    }

    /// ///////////////////////////////////////////////////////////////////////

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> read() {
        return reservationService.read();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
        @RequestBody ReservationRequest reservationRequest) {
        reservationRequest.validate();

        Reservation reservation = reservationService.createReservation(reservationRequest);
        return ResponseEntity
            .created(URI.create("/reservations/" + reservation.getId()))
            .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
