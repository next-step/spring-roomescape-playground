package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.Domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> getReservations() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
        {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
            return reservation;
        });
    }

    @Override
    public Long createReservation(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        if (reservation == null || !reservation.isValid()) {
            throw new IllegalArgumentException("누락된 사항이 있습니다. 확인해주세요.");
        }

        return insertWithKeyHolder(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        String sql = "delete from reservation where id = ? ";
        jdbcTemplate.update(sql, id);
    }

    private Long insertWithKeyHolder(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
