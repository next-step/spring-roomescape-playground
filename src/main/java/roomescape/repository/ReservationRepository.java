package roomescape.repository;

import static roomescape.domain.Reservation.RESERVATION_LENGTH_MINUTES;

import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {
    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getObject("datetime", LocalDateTime.class)
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation saveReservation(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("datetime", reservation.getDateTime());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservation.getName(), reservation.getDateTime());
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, datetime FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public int deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public boolean countConflictingReservations(LocalDateTime dateTime) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE DATEADD(MINUTE, CAST(? AS INT), datetime) > CAST(? AS SMALLDATETIME) AND datetime < DATEADD(MINUTE, CAST(? AS INT), CAST(? AS SMALLDATETIME))";
        int conflictingReservationCount = jdbcTemplate.queryForObject(sql, Integer.class, RESERVATION_LENGTH_MINUTES,
                dateTime, RESERVATION_LENGTH_MINUTES, dateTime);
        return conflictingReservationCount > 0;
    }
}
