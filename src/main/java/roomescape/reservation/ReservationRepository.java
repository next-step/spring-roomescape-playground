package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Time.Time;
import roomescape.Time.TimeRepository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;
    private final TimeRepository timeRepository;

    public ReservationRepository(JdbcTemplate jdbcTemplate, TimeRepository timeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> selectAll (){
        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.time as time_value \n" +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        List<Reservation> reservations1 = jdbcTemplate.query(sql , (resultSet, rowNum) -> {
            List<Time> timeList= timeRepository.selectAll();
            Time newTime = new Time(resultSet.getLong("time_id"), timeList.get(rowNum).getTime());
            Reservation reservation = new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    newTime);
            return reservation;
        });
        return  reservations1;
    }

    public Reservation insertReservation(Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, newReservation.getName());
            ps.setString(2, newReservation.getDate());
            ps.setLong(3, newReservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, newReservation.getName(), newReservation.getDate(), newReservation.getTime());
    }


    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowNum = jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
