package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationEntity;
import roomescape.domain.TimeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationEntity> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, this::mapRowToReservation);
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id WHERE r.id = ?";
        return jdbcTemplate.query(sql, this::mapRowToReservation, id)
                .stream()
                .findFirst();
    }

    @Override
    public ReservationEntity save(ReservationEntity reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.name());
        parameters.put("date", reservation.date());
        parameters.put("time_id", reservation.time().id());

        long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new ReservationEntity(id, reservation.name(), reservation.date(), reservation.time());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private ReservationEntity mapRowToReservation(ResultSet rs, int rowNum) throws SQLException {
        return new ReservationEntity(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                new TimeEntity(rs.getLong("time_id"), rs.getString("time_value"))
        );
    }
}
