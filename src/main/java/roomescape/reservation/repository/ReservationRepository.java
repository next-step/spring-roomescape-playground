package roomescape.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

@Repository
public class ReservationRepository {
    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                resultSet.getObject("time", Time.class).withId(resultSet.getLong("time_id"))
        );
        return reservation.withId(resultSet.getLong("reservation_id"));
    };

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
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return reservation.withId(id);
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value FROM reservation AS r INNER JOIN time AS t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public int deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int countConflictingReservations(LocalDate date, Time time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = CAST(? AS DATE) AND time_id = CAST(? AS BIGINT)";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time.getId());
    }
}
