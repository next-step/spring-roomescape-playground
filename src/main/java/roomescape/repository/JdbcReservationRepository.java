package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, this::mapRowToReservation);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.name());
        params.put("date", reservation.date());
        params.put("time", reservation.time());

        int id = simpleJdbcInsert.executeAndReturnKey(params).intValue();
        return new Reservation(id, reservation.name(), reservation.date(), reservation.time());
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int resultRow = jdbcTemplate.update(sql, id);
        return resultRow > 0;
    }

    private Reservation mapRowToReservation(ResultSet resultSet, int rowNum) throws SQLException {
        return new Reservation(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
    }
}
