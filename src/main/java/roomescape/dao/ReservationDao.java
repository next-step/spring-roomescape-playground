package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.ReservationNotFoundException;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
            SELECT
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.time AS time_value
            FROM reservation AS r 
            INNER JOIN time AS t 
                ON r.time_id = t.id
            """;
        return jdbcTemplate.query(sql, ((rs, rowNum) ->
                new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    new Time(
                        rs.getLong("time_id"),
                        LocalTime.parse(rs.getString("time_value"))
                    )
                )
            )
        );
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate().toString());
        params.put("time_id", reservation.getTime().getId());

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(),
            reservation.getTime()
        );
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new ReservationNotFoundException(id);
        }
    }
}

