package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );

        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );
    };

    public Long insert(Reservation reservation, Long timeId) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connect -> {
            PreparedStatement ps = connect.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long id) {
        if (countReservationById(id) == 0) {
            throw new IllegalArgumentException("해당 예약이 존재하지 않습니다.");
        }

        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    private int countReservationById(Long id) {
        String sql = "SELECT count(*) FROM reservation WHERE id = ?";
        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return rowCount;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value "
                + "FROM reservation as r "
                + "inner join time as t on r.time_id = t.id";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
