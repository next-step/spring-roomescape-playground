package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IdempotencyRepository {
    private final JdbcTemplate jdbcTemplate;

    public boolean exists(String key) {
        String sql = "SELECT count(*) FROM idempotency_keys WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, key);
        return count != null && count > 0;
    }

    public void save(String key) {
        String sql = "INSERT INTO idempotency_keys (id) VALUES (?)";
        jdbcTemplate.update(sql, key);
    }

    public Long getReservationId(String key) {
        try {
            String sql = "SELECT reservation_id FROM idempotency_keys WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, Long.class, key);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(String key, Long reservationId) {
        String sql = "INSERT INTO idempotency_keys (id, reservation_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, key, reservationId);
    }
}
