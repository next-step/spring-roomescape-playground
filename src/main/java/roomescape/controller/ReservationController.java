package roomescape.controller;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationJdbcDAO;
import roomescape.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationJdbcDAO reservationJdbcDAO;

    public ReservationController(ReservationJdbcDAO reservationJdbcDAO) {
        this.reservationJdbcDAO = reservationJdbcDAO;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationJdbcDAO.getAll();
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {

        Reservation newReservation = reservationJdbcDAO.create(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservationDetail(@PathVariable int id) {
        return reservationJdbcDAO.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable int id) {
        reservationJdbcDAO.delete(id);
    }


}
