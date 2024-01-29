package roomescape.infrastructure.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation")
                                                                  .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    @Override
    public Reservation save(final Reservation reservation) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time", reservation.getTime());

        final long insertedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(insertedId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public boolean existsById(final Long id) {
        final String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";
        final Long count = jdbcTemplate.queryForObject(sql, Long.class, id);

        return count != null && count > 0;
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    private static RowMapper<Reservation> getReservationRowMapper() {
        return (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime());
    }
}
