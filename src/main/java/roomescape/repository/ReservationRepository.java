package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {
    private static final String TABLE_NAME = "reservation";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> reservationMapper = (rs, rowNum) -> {
        return Reservation.of(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        );
    };

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String query = "SELECT id, name, date, time FROM " + TABLE_NAME;

        return jdbcTemplate.query(query, reservationMapper);
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> params = Map.of(
                "name", reservation.name(),
                "date", reservation.date(),
                "time", reservation.time()
        );

        Number id = simpleJdbcInsert.executeAndReturnKey(params);

        return Reservation.of(id.intValue(), reservation.name(), reservation.date(), reservation.time());
    }

    public void deleteById(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        jdbcTemplate.update(query, id);
    }
}
