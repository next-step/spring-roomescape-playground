package roomescape.reservationManage;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.timeManage.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReservationDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Reservation insert(String name, String date, Long time) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("date", date);
        parameters.put("time_id", time);

        Number generatedKey = simpleJdbcInsert.executeAndReturnKey(parameters);
        return findById(generatedKey.longValue());
    }

    public Reservation findById(Long id) {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value " +
                "FROM reservation AS r INNER JOIN time AS t ON r.time_id = t.id WHERE r.id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, reservationRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value " +
                "FROM reservation AS r INNER JOIN time AS t ON r.time_id = t.id";
        return namedParameterJdbcTemplate.query(sql, reservationRowMapper);
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Time time = new Time(
                rs.getLong("time_id"),
                rs.getString("time_value")
        );
        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                time
        );
    };
}
