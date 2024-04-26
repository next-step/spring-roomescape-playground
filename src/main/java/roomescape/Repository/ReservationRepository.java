package roomescape.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Reservation> getAllReservation() {
        List<Reservation> reservationList = jdbcTemplate.query(
    String sql = """
    select 
      r.id as reservation_id, 
      r.name, r.date, 
      t.id as time_id, 
      t.time as time_value 
    from reservation as r 
    inner join time as t on r.time_id = t.id
    """;
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            new Time(resultSet.getLong("time_id"), resultSet.getString("time_value"))
                    );
                    return reservation;
                });
        return reservationList;
    }

    public Reservation createdReservation(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(), new Time(id, reservation.getTime().getTime()));
        return newReservation;
    }
    public void deleteReservationById(Long id){
        String sql = "DELETE FROM reservation WHERE id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation where id = ?", Integer.class, id);
            jdbcTemplate.update(sql, id);
        } catch (IncorrectResultSizeDataAccessException error) {
            throw error;
        }
    }

    public String getTimeById(Long id) {
        String sql = "select time from time where id = ?";

        String time = jdbcTemplate.queryForObject(sql, String.class, id);
        return time;
    }
}
