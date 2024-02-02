package roomescape.domain;
import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ReservationDao{
    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (ResultSet, rownumber) -> {
        Time time = new Time(
                ResultSet.getInt("t_id"),
                ResultSet.getString("time")
        );
        Reservation reservation = new Reservation(
                ResultSet.getInt("id"),
                ResultSet.getString("name"),
                ResultSet.getString("date"),
                ResultSet.getString("time"));
        return reservation;
    };

    public List<Reservation> getAllReservations() {
        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.time as time_value \n" +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation getReservationById(int id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, reservationRowMapper);
    }
    public void deleteReservationById(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public int save(Reservation reservation){
        String sql = "insert into reservation (name,date,time_id) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1,reservation.getName());
            ps.setString(2,reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}