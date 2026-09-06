package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, time FROM time ORDER BY id";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> ReservationTime.restore(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("time"))
        ));
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> ReservationTime.restore(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("time"))
        ), id).stream().findFirst();
    }

    @Override
    public boolean existsByTime(LocalTime time) {
        String sql = "SELECT COUNT(1) FROM time WHERE time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, time.toString());
        return count != null && count > 0;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservationTime.getTime().toString());
            return statement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("시간 ID를 생성하지 못했습니다.");
        }
        return ReservationTime.restore(generatedId.longValue(), reservationTime.getTime());
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
