package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.request.ReservationCreateRequestDto;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );
    };

    public List<Reservation> findAllReservations() {
        String sql = """
                SELECT\s
                    r.id as reservation_id,\s
                    r.name,\s
                    r.date,\s
                    t.id as time_id,\s
                    t.time as time_value\s
                FROM reservation as r inner join time as t on r.time_id = t.id""";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Long insert(ReservationCreateRequestDto reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date());
            ps.setString(3, reservation.timeId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
