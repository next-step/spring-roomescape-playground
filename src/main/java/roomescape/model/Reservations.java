package roomescape.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.model.errors.ReservationNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Reservations {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getReservationList() {
        return jdbcTemplate.query("SELECT * FROM reservation", Reservation::new);
    }

    public void add(Reservation reservation) {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) values (?, ? ,?)",
                reservation.name(), reservation.date(), reservation.time());
    }

    public void removeById(long deletingId) throws ReservationNotFoundException {
        try {
            jdbcTemplate.update("DELETE FROM reservation WHERE id = ?",deletingId);
        } catch (Exception e) {
            throw new ReservationNotFoundException();
        }
    }
}

