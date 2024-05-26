package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("reservations")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select"
                + " r.id as reservation_id, r.name, r.date,"
                + " t.id as time_id, t.time"
                + " from reservations as r inner join times as t"
                + " on r.time_id = t.id";
        return namedParameterJdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                rs.getObject("time", LocalTime.class)
                        )
                )
        );
    }

    @Override
    public Reservation save(final ReservationRequest request, Long timeId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", request.name())
                .addValue("date", request.date())
                .addValue("timeId", timeId);
        final long savedId = jdbcInsert.executeAndReturnKey(param).longValue();
        return new Reservation(
                savedId,
                request.name(),
                request.date(),
                new ReservationTime(timeId));
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "delete from reservations where id = :id";
        Map<String, Object> param = Map.of("id", id);
        final int rowNum = namedParameterJdbcTemplate.update(sql, param);
        if (rowNum == 0) {
            throw new ReservationException(ErrorMessage.INVALID_ID_REQUEST);
        }
    }

}