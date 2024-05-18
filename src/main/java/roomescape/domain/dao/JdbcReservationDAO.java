package roomescape.domain.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.RequestReservationDTO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDTO;

@Repository
public class JdbcReservationDAO implements ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationDTO> reservationDtoRowMapper = (resultSet, rowNum) -> {
        return new ReservationDTO(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
    };

    @Override
    public List<ReservationDTO> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, reservationDtoRowMapper);
    }

    @Override
    public Reservation insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
        return reservation;
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
