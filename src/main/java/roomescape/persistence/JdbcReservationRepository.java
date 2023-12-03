package roomescape.persistence;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.repository.ReservationRepository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        final Long reservationId = rs.getLong("reservation_id");
        final String name = rs.getString("name");
        final String date = rs.getString("date");
        final Long timeId = rs.getLong("time_id");
        final String timeValue = rs.getString("time_value");
        return new Reservation(reservationId, name, LocalDate.parse(date), new Time(timeId, LocalTime.parse(timeValue)));
    };
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(final Reservation reservation) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into reservation  (name, date, time_id) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void delete(final Long id) {
        final String sql = "delete from reservation where id = ?";
        int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new IllegalArgumentException("해당 id를 가진 예약이 존재하지 않습니다.");
        }
    }

    @Override
    public Reservation findById(final Long id) {
        try {
            final String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                    "FROM reservation as r inner join time as t on r.time_id = t.id where r.id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (final DataAccessException exception) {
            throw new IllegalArgumentException("해당 id를 가진 예약이 존재하지 않습니다.");
        }
    }
}
