package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> SelectAll (){
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservations1 = jdbcTemplate.query(sql , (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time"));
            return reservation;
        });
        return  reservations1;
    }

    public Reservation selectReservation(Reservation reservation) {
        Reservation newReservation = Reservation.toEntity(reservation);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, newReservation.getName());
            ps.setString(2, newReservation.getDate());
            ps.setString(3, newReservation.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        newReservation.setId(id);
        return newReservation;
    }


    public void DeleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowNum = jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
