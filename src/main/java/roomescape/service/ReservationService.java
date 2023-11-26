package roomescape.service;

import static roomescape.validation.ReservationValidation.validate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final AtomicLong index = new AtomicLong(1);

    // 예약 추가
    public  ResponseEntity<Reservation> reserve(Reservation reservation) {
        validate(reservation);

        Reservation newReservation = Reservation.createWithId(reservation, index.getAndIncrement());
        jdbcTemplate.update("insert into reservation (name, date, time) values (?, ?, ?)", reservation.name(),
                reservation.date(), reservation.time());
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newReservation);
    }

    // 예약 조회
    @Transactional(readOnly = true)
    public ResponseEntity<List<Reservation>> findReservations() {
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

    // 예약 삭제
    public ResponseEntity<Void> deleteReservation(String id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
        return ResponseEntity.noContent().build();
    }
}
