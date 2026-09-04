package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "from reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("time_id"),
                            resultSet.getObject("time_value", LocalTime.class)
                    );
                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getObject("date", LocalDate.class),
                            time
                    );
                    return reservation;
                });
    }


    public int delete(Long id) {
        int delete = jdbcTemplate.update("delete from reservation where id = ?", id);
        return delete;
    }

    public Long insertWithKeyHolder(String name, LocalDate date, Long timeId) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setObject(2, date);
            ps.setObject(3, timeId);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
