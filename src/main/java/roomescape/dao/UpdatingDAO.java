package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationDto;

import java.sql.PreparedStatement;

@Repository
public class UpdatingDAO {

    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insertReservation(ReservationDto reservationDto) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationDto.getName());
            ps.setString(2, reservationDto.getDate());
            ps.setString(3, reservationDto.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservationDto.getName(), reservationDto.getDate(), reservationDto.getTime());
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id =?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
