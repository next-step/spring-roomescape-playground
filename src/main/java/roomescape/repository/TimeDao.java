package roomescape.repository;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Map;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, time FROM time ORDER BY id";
        return jdbcTemplate.query(sql, timeRowMapper());
    }

    public ReservationTime save(ReservationTime reservationTime) {
        Number key = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "time", reservationTime.getTime()
        ));
        return new ReservationTime(key.longValue(), reservationTime.getTime());
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, id);
        return updatedRows > 0;
    }

    private RowMapper<ReservationTime> timeRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("time")
        );
    }
}
