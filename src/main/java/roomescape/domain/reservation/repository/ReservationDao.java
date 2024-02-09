package roomescape.domain.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDto;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.time.entity.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("time_id"),
                resultSet.getString("time_value")
        );

        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                time
        );
        return reservation;
    };

    public List<Reservation> findAllReservations() {
        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.time as time_value \n" +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Long insert(ReservationCreateRequestDto requestDto) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, requestDto.name());
            ps.setString(2, requestDto.date());
            ps.setString(3, requestDto.timeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation findReservationById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}