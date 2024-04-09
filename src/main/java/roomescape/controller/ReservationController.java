package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.dto.Reservation;
import roomescape.dto.Time;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private ReservationDAO reservationDAO;
    private TimeDAO timeDAO;
    public ReservationController(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        // TODO: 저장된 모든 reservation 정보를 반환한다.
        return ResponseEntity.ok(reservationDAO.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        // TODO: reservation 정보를 받아서 생성한다.
        Long id = reservationDAO.insertWithKeyHolder(reservation);
        Time time = timeDAO.findTimeById(Long.parseLong(reservation.getTime().getTime()));
        Reservation newReservation = Reservation.toEntity(reservation, time, id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 reservation을 삭제한다.
        reservationDAO.delete(id);
        return ResponseEntity.noContent().build();
    }

}