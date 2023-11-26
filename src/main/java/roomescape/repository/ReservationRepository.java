package roomescape.repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final AtomicLong index = new AtomicLong(1);

    public Reservation save(Reservation reservation) {
        Reservation newReservation = Reservation.createWithId(reservation, index.getAndIncrement());
        jdbcTemplate.update("insert into reservation (name, date, time) values (?, ?, ?)", reservation.name(),
                reservation.date(), reservation.time());
        return newReservation;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));
    }

    public void delete(String id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
