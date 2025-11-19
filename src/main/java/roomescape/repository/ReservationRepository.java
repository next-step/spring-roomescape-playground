package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationMapper = (rs, rowNum) -> {
        return new Reservation(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        );
    };

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String query = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(query, reservationMapper);
    }

    public Reservation save(Reservation reservation) {
        String query = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        jdbcTemplate.update(query, reservation.name(), reservation.date(), reservation.time());

        // get inserted row id
        int id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM reservation", Integer.class);

        return new Reservation(id, reservation.name(), reservation.date(), reservation.time());
    }

    public void deleteById(int id) {
        String query = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(query, id);
    }
}
