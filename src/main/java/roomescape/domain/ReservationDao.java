package roomescape.domain;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ReservationDao{
    private final JdbcTemplate jdbcTemplate;
    public ReservationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rownumber) -> {
        final Time time = new Time(
                resultSet.getInt("time_id"),
                resultSet.getTime("time_value").toLocalTime());
        Reservation reservation = new Reservation(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time);
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
        SimpleJdbcInsert simplejdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation").usingColumns("name","date","time_id").usingGeneratedKeyColumns("id");
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());
        Number key = simplejdbcInsert.executeAndReturnKey(parameterSource);
        return key.intValue();
    }
}