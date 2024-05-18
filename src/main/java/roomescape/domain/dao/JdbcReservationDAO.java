package roomescape.domain.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        reservation.setId(keyHolder.getKey().longValue());
        return reservation;
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
