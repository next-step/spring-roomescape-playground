package roomescape.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (ResultSet, rownumber)
            -> new Reservation(
            ResultSet.getInt("id"),
            ResultSet.getString("name"),
            ResultSet.getString("date"),
            ResultSet.getString("time")
    );

    public List<Reservation> getAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
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
    public long save(ReservationDto reservationDto){
        String sql = "insert into customers (first_name, last_name) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1,reservationDto.getName());
            ps.setString(2,reservationDto.getDate());
            ps.setString(3,reservationDto.getTime());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}