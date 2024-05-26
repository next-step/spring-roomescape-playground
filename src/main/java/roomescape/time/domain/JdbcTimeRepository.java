package roomescape.time.domain;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.exception.NotExistTimeException;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Time> findAll() {
        final String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ));
    }

    @Override
    public Time findById(final Long id) {
        final String sql = "SELECT * FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ), id);
    }

    @Override
    public Time save(final Time time) {
        final String sql = "INSERT INTO time (time) VALUES (?)";

        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.time());
            return ps;
        }, keyHolder);

        final long generatedKey = keyHolder.getKey().longValue();
        return new Time(generatedKey, time.time());
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM time WHERE id = ?";
        final int updatedRowCount = jdbcTemplate.update(sql, id);
        if (updatedRowCount == 0) {
            throw new NotExistTimeException("해당 시간을 찾을 수 없습니다.");
        }
    }
}
