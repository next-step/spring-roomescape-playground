package roomescape.reservation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dao.QueryingDAO;
import roomescape.reservation.domain.Reservation;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private List<Reservation> reservations; // = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    private QueryingDAO queryingDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping
    public ResponseEntity<List<Reservation>> read() {

        queryingDAO = new QueryingDAO(jdbcTemplate);
        reservations = queryingDAO.getAllReservations();

        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
        if (reservation.getName() == null || reservation.getDate() == null || reservation.getTime().isEmpty()) {
            throw new NotFoundReservationException("빈 값이 존재합니다!");
        }
        if (reservations.stream().anyMatch(r -> Objects.equals(r.getName(), reservation.getName()))) {
            throw new NotFoundReservationException("이미 존재하는 id 입니다!");
        }

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("id를 찾을 수 없습니다!"));

        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public static class NotFoundReservationException extends RuntimeException {
        public NotFoundReservationException(String message) {
            super(message);
        }
    }
}