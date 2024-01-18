package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Controller
public class ReservationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationDAO reservationDAO = new ReservationDAO(jdbcTemplate);

    public ReservationController() {
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        if(Reservation.checkValidity(reservation)) throw new NoParameterException();

        Long id = reservationDAO.insertNewReservation(reservation);
        Reservation newReservation = Reservation.toEntity(reservation, id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservationList = reservationDAO.findAllReservations();
        return ResponseEntity.ok().body(reservationList);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Void> update(@RequestBody Reservation newReservation, @PathVariable Long id) {
        Reservation reservation = reservationDAO.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDAO.insertNewReservation(newReservation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservationDAO.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDAO.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
