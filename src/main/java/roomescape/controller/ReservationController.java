package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationQueryDao;
import roomescape.dao.ReservationUpdatingDAO;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ReservationController {

    @Autowired
    private ReservationQueryDao reservationQueryDao;
    private ReservationUpdatingDAO reservationUpdatingDAO;

    public ReservationController(ReservationQueryDao reservationQueryDao, ReservationUpdatingDAO reservationUpdatingDAO) {
        this.reservationQueryDao = reservationQueryDao;
        this.reservationUpdatingDAO = reservationUpdatingDAO;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationQueryDao.getAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        reservationUpdatingDAO.insertReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int removed = reservationUpdatingDAO.deleteReservation(id);
        if (removed == 0) {
            throw new NoSuchElementException("삭제할 항목이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
