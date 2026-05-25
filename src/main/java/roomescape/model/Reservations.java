package roomescape.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.model.errors.ReservationNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Reservations {
    private final List<Reservation> reservations;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservations = new ArrayList<Reservation>();
    }

    public List<Reservation> getReservationList() {

        return jdbcTemplate.query("SELECT * FROM reservation",
                (resultSet, rowNum) -> {
                    return (Reservation) new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                }
        );
    }

    public void add(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeById(long deletingId) throws ReservationNotFoundException {
        Reservation toDelete = this.reservations.stream()
                .filter(reservation -> deletingId == reservation.id())
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
        this.reservations.remove(toDelete);
    }
}

