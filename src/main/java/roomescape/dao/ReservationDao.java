package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.RequestReservation;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
    );

    public ReservationDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(final RequestReservation requestReservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", requestReservation.name());
        parameters.put("date", requestReservation.parseDate());
        parameters.put("time", requestReservation.parseTime());

        Number newId = jdbcInsert.executeAndReturnKey(parameters);

        return new Reservation(
                newId.longValue(),
                requestReservation.name(),
                requestReservation.parseDate(),
                requestReservation.parseTime()
        );
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Reservation> findById(final long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = :id";
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
