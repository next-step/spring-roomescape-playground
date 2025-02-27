package roomescape.domain.reservationTime.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservationTime.domain.ReservationTime;
import roomescape.global.exception.RoomescapeNotFoundException;

@Repository
public class ReservationTimeRepository {

    private static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getObject("time", LocalTime.class)
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepository(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservationTime")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime create(final ReservationTime reservationTime) {
        Map<String, Object> parameters = Map.of(
                "time", reservationTime.getTime()
        );
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters)
                .longValue();

        return new ReservationTime(id, reservationTime.getTime());
    }

    public ReservationTime findById(final long id) {
        try {
            String sql = "select * from reservationTime where id = ?";

            return jdbcTemplate.queryForObject(sql, RESERVATION_TIME_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException exception) {
            throw new RoomescapeNotFoundException("예약 시간을 찾을 수 없습니다. id: " + id);
        }
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservationTime ORDER BY time ASC";

        return jdbcTemplate.query(sql, RESERVATION_TIME_ROW_MAPPER);
    }

    public boolean remove(final long id) {
        String sql = "DELETE FROM reservationTime WHERE id = ?";

        return jdbcTemplate.update(sql, id) > 0;
    }
}
