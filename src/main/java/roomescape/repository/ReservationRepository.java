package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.Time;

@Repository
public class ReservationRepository {
    private static final String TABLE_NAME = "reservation";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> reservationMapper = (rs, rowNum) -> {
        Time time = Time.of(rs.getInt("time_id"), rs.getTime("time_value").toLocalTime());

        return Reservation.of(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                time
        );
    };

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String query = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value FROM " + TABLE_NAME + " AS r INNER JOIN time AS t ON r.time_id = t.id";

        return jdbcTemplate.query(query, reservationMapper);
    }

    public Optional<Reservation> findById(int id) {
        String query = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value FROM " + TABLE_NAME + " AS r INNER JOIN time AS t ON r.time_id = t.id WHERE r.id = ?";
        List<Reservation> reservations = jdbcTemplate.query(query, reservationMapper, id);

        System.out.println(reservations);

        return reservations.isEmpty() ? Optional.empty() : Optional.of(reservations.get(0));
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> params = Map.of(
                "name", reservation.name(),
                "date", reservation.date(),
                "time_id", reservation.time().id()
        );

        Number id = simpleJdbcInsert.executeAndReturnKey(params);

        return Reservation.of(id.intValue(), reservation.name(), reservation.date(), reservation.time());
    }

    public void deleteById(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        jdbcTemplate.update(query, id);
    }

    public boolean existsByTimeId(int timeId) {
        String query = "SELECT EXISTS (SELECT 1 FROM " + TABLE_NAME + " WHERE time_id = ?)";

        return jdbcTemplate.queryForObject(query, Boolean.class, timeId);
    }
}
