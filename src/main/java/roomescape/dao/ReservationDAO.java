package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT " +
                "r.id as reservation_id, " +
                "r.name, r.date, " +
                "t.id as time_id, " +
                "t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Time time = new Time(resultSet.getLong("time_id"),
                            resultSet.getString("time_value"));
                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            time
                    );
                    return reservation;
                });
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
