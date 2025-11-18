package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class IdempotencyRepository {
    private final JdbcTemplate jdbcTemplate;


    public IdempotencyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean exists(String key) {
        String sql = "SELECT count(*) FROM idempotency_keys WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, key);
        return count != null && count > 0;
    }

    public void save(String key) {
        String sql = "INSERT INTO idempotency_keys (id) VALUES (?)";
        jdbcTemplate.update(sql, key);
    }
}
