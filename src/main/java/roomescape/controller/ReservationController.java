package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.dto.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*@GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        // TODO: 저장된 모든 reservation 정보를 반환한다.
        return ResponseEntity.ok().body(reservations);
    }*/

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        // TODO: 저장된 모든 reservation 정보를 반환한다.
        reservations = new ReservationDAO(jdbcTemplate).findAllReservations();
        return ResponseEntity.ok().body(reservations);
    }

    /*@PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        // TODO: reservation 정보를 받아서 생성한다.
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }*/

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        // TODO: reservation 정보를 받아서 생성한다.
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        Long id = new ReservationDAO(jdbcTemplate).insertWithKeyHolder(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }

    /*@DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 reservation을 삭제한다.
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }*/

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO: url 상의 id 정보를 받아 reservation을 삭제한다.
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        new ReservationDAO(jdbcTemplate).delete(id);

        return ResponseEntity.noContent().build();
    }

}