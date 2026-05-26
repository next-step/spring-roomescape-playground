package roomescape.timeslot.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservations.model.Reservation;
import roomescape.timeslot.dto.request.TimeslotRequest;
import roomescape.timeslot.model.Timeslot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class TimeslotRespository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Timeslot> rowMapper = (rs, rowNum) -> new Timeslot(
            rs.getLong("id"),
            rs.getObject("timeslot", LocalTime.class)
    );

    public TimeslotRespository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addTimeslot(Timeslot timeslot) {
        String query = "INSERT INTO timeslot(timeslot) " +
                "VALUES (:timeslot)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("timeslot", timeslot.getTimeslot());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(query, parameterSource, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int deleteTimeslotById(Long id) {
        String query = "DELETE FROM timeslot " +
                "WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcTemplate.update(query, parameterSource);
    }

    public List<Timeslot> getAllTimeslots() {
        String query = "SELECT * FROM timeslot";
        return namedParameterJdbcTemplate.query(query, Map.of(), rowMapper);
    }
}
