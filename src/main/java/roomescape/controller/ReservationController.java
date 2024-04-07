package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ReservationController {

    @Autowired
    private QueryingDAO queryingDAO;
    private UpdatingDAO updatingDAO;

    public ReservationController(QueryingDAO queryingDAO, UpdatingDAO updatingDAO) {
        this.queryingDAO = queryingDAO;
        this.updatingDAO = updatingDAO;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = (List<Reservation>) queryingDAO.getAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        updatingDAO.insertReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int removed = updatingDAO.deleteReservation(id);
        if (removed == 0) {
            throw new NoSuchElementException("삭제할 항목이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }
}
