package roomescape.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            Time.of(resultSet.getLong("time_id"),
                    resultSet.getTime("time").toLocalTime())
    );

    public ReservationDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(final String name, final LocalDate date, final Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("date", date);
        parameters.put("time_id", time.id());

        Number newId = jdbcInsert
                .usingColumns("name", "date", "time_id")
                .executeAndReturnKey(parameters);

        return Reservation.of(newId.longValue(), name, date, time);
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT 
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time as time_value
                FROM reservation as r
                INNER JOIN time as t ON r.time_id = t.id
                """;

        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Reservation> findById(final long id) {
        String sql = """
                SELECT 
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.time as time_value
                FROM reservation r
                INNER JOIN time t ON r.time_id = t.id
                WHERE r.id = :id
                """;

        Map<String, Object> params = Map.of("id", id);
        List<Reservation> results = namedParameterJdbcTemplate.query(sql, params, rowMapper);
        return results.stream().findFirst();
    }

    public void deleteById(final long id) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
