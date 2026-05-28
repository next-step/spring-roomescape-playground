package roomescape.repository;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation ORDER BY id";
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    public boolean existsByDateAndTime(String date, String time) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return count != null && count > 0;
    }

    public Reservation save(Reservation reservation) {
        Number key = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time", reservation.getTime()
        ));
        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, id);
        return updatedRows > 0;
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
    }
}
