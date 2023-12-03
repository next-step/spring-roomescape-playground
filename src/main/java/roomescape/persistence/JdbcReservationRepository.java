package roomescape.persistence;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        final Long id = rs.getLong("id");
        final String name = rs.getString("name");
        final LocalDate date = rs.getDate("date").toLocalDate();
        final LocalTime time = rs.getTime("time").toLocalTime();
        return new Reservation(id, name, date, time);
    };
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(final Reservation reservation) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into reservation  (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select * from reservation";
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
            final String sql = "select * from reservation where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (final DataAccessException exception) {
            throw new IllegalArgumentException("해당 id를 가진 예약이 존재하지 않습니다.");
        }
    }
}
