package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDAO(JdbcTemplate jdbcTemplate, TimeDAO timeDAO) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
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

    public List<Reservation> findAllReservations() {
        String sql = """
                         SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value
                         FROM reservation as r inner join time as t on r.time_id = t.id
                         """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Reservation insert(Reservation reservation) {
        Time time = reservation.getTime();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO time(time) VALUES (?)", new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Long timeId = keyHolder.getKey().longValue();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)", new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        Long reservationId = keyHolder.getKey().longValue();
        return new Reservation(reservationId, reservation.getName(), reservation.getDate(), new Time(timeId, time.getTime()));
    }

    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int row = jdbcTemplate.update(sql, id);
        if (row == 0) {
            throw new NotFoundReservationException(id);
        }
        return row;
    }
}
