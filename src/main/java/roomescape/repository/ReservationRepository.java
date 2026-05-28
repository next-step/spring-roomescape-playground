package roomescape.repository;

import java.time.LocalDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r " +
                "INNER JOIN time as t ON r.time_id = t.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Time time = new Time(
                    rs.getLong("time_id"),
                    rs.getTime("time_value").toLocalTime()
            );
            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    time
            );
        });
    }

    public Long save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", java.sql.Date.valueOf(reservation.getDate()))
                .addValue("time_id", reservation.getTimeId());
        return reservationInsert.executeAndReturnKey(params).longValue();
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    public List<Reservation> findByDate(LocalDate date) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r " +
                "INNER JOIN time as t ON r.time_id = t.id " +
                "WHERE r.date = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Time time = new Time(
                            rs.getLong("time_id"),
                            rs.getTime("time_value").toLocalTime()
                    );
                    return new Reservation(
                            rs.getLong("reservation_id"),
                            rs.getString("name"),
                            rs.getDate("date").toLocalDate(),
                            time
                    );
                },
                java.sql.Date.valueOf(date)
        );
    }
}
