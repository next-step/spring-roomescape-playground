package roomescape.reservation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.ReservationDTO;
import roomescape.reservation.domain.Reservation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDAO {

    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getString("time")
                    );
                    return reservation;
                });
    }

    public Long insertWithKeyHolder(ReservationDTO reservationDTO) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[]{"id"});
                    ps.setString(1, reservationDTO.getName());
                    ps.setString(2, reservationDTO.getDate());
                    ps.setString(3, reservationDTO.getTime());
                    return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        System.out.printf(String.valueOf(id));
        return id;
    }

//    public void update(Long id, ReservationDTO reservationDTO) {
//        String sql = "UPDATE reservation SET name = ?, date = ?, time = ? WHERE id = ?";
//
//        jdbcTemplate.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, reservationDTO.getName());
//            ps.setString(2, reservationDTO.getDate());
//            ps.setString(3, reservationDTO.getTime());
//            ps.setLong(4, id);
//            return ps;
//        });
//    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }


}
