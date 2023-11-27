package roomescape.reservation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dao.QueryRepository;
import roomescape.reservation.dao.UpdateRepository;
import roomescape.reservation.domain.Reservation;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private QueryRepository queryRepository;
    private UpdateRepository updateRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping
    public ResponseEntity<List<Reservation>> read() {


        queryRepository = new QueryRepository(jdbcTemplate);

        return ResponseEntity.status(HttpStatus.OK).body(queryRepository.getAllReservations());

    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
        if (reservation.getName() == null || reservation.getDate() == null || reservation.getTime() == null) {
            throw new NotFoundReservationException("빈 값이 존재합니다!");
        }


        updateRepository = new UpdateRepository(jdbcTemplate);

        Reservation newReservation = Reservation.toEntity(reservation);

        Long id = updateRepository.insert(newReservation);


        newReservation.setId(id);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/reservations/" + id))
                .body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        updateRepository = new UpdateRepository(jdbcTemplate);
        updateRepository.delete(id);

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