package roomescape.controller;

import static roomescape.utils.ErrorMessage.*;
import static roomescape.validation.ReservationValidation.validate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    //private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);


    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        validate(reservation);

        Reservation newReservation = Reservation.createWithId(reservation, index.getAndIncrement());
        //reservations.add(newReservation);
        jdbcTemplate.update("insert into reservation (name, date, time) values (?, ?, ?)", reservation.name(),
                reservation.date(), reservation.time());
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservation() {
        List<Reservation> reservations = jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));

        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable AtomicLong id) {
//        Reservation reservation = reservations.stream()
//                .filter(it -> Objects.equals(it.id().get(), id.get()))
//                .findFirst()
//                .orElseThrow(() -> new NotFoundReservationException(NON_EXISTING_RESERVATION));
//
//        reservations.remove(reservation);

        return ResponseEntity.noContent().build();
    }


}
