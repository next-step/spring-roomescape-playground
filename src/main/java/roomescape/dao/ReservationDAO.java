package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation addReservation(final Reservation reservation) {
        final var query = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(query,
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getTime().toString());

        final var fetchSql = "SELECT * FROM reservation ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(fetchSql, reservationRowMapper);
    }

    public Optional<Reservation> findByID(final int id) {
        final var query = "SELECT * FROM reservation WHERE id = ?";
        List<Reservation> results = jdbcTemplate.query(query, reservationRowMapper, id);
        return results.stream().findFirst();
    }

    public void deleteReservation(final int id) {
        final var query = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public List<Reservation> findAll() {
        final var query = "SELECT * FROM reservation";
        return jdbcTemplate.query(query, reservationRowMapper);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date"), DateTimeFormatter.ISO_DATE),
            LocalTime.parse(resultSet.getString("time"), DateTimeFormatter.ISO_TIME)
    );
}
