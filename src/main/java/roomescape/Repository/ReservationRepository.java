package roomescape.Repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Exception.NotFoundReservationException;
import roomescape.Model.Reservation;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationRepository {

    private JdbcTemplate jdbcTemplate;
    public ReservationRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public List<Reservation> getAllReservations(){
        String sql="SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql,actorRowMapper);
    }

    public Reservation getReservationById(Long id){
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject(sql,actorRowMapper,id);
        }
        catch (EmptyResultDataAccessException e){
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        }
    }

    public long createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteReservationById(Long id){
        String sql="DELETE FROM reservation WHERE id = ?";
        try {
            jdbcTemplate.update(sql,id);
        }
        catch (EmptyResultDataAccessException e){
            throw new NotFoundReservationException("예약을 찾을 수 없습니다.");
        }
    }
}
