package roomescape.domain.reservation.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ResponseReservation;
import roomescape.domain.reservation.dto.ReservationDTO;
import roomescape.domain.time.Time;
import roomescape.domain.time.dao.TimeDAO;
import roomescape.domain.time.dto.TimeDTO;

@Repository
public class JdbcReservationDAO implements ReservationDAO {
    private final TimeDAO timeDAO;
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDAO(JdbcTemplate jdbcTemplate, TimeDAO timeDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeDAO = timeDAO;
    }

    private final RowMapper<ReservationDTO> reservationDtoRowMapper = (resultSet, rowNum) -> {
        return new ReservationDTO(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new Time(resultSet.getLong("time_id"), resultSet.getString("time_value"))
        );
    };

    @Override
    public List<ReservationDTO> findAll() {
        String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.time as time_value \n"
                + "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationDtoRowMapper);
    }

    @Override
    public ResponseReservation insert(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        reservation.setId(keyHolder.getKey().longValue());
        TimeDTO timeDTO = timeDAO.findById(reservation.getTime().getId());
        return new ResponseReservation(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                timeDTO.time());
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
