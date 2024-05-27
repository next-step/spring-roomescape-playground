package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationEntity;
import roomescape.dto.ReservationDTO;

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
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, this::mapRowToReservation);
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        return jdbcTemplate.query(sql, this::mapRowToReservation, id)
                .stream()
                .findFirst();
    }

    @Override
    public ReservationEntity save(ReservationEntity reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.name());
        parameters.put("date", reservation.date());
        parameters.put("time", reservation.time());

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
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        );
    }
}
