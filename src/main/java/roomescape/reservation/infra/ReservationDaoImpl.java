package roomescape.reservation.infra;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundReservationException;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationDao;
import roomescape.time.Time;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> Reservation.ofExist(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            Time.ofExist(
                    rs.getLong("time_id"),
                    rs.getTime("time_value").toLocalTime()
            )
    );

    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT " +
                "r.id as reservation_id," +
                "r.name," +
                "r.date," +
                "t.id as time_id," +
                "t.time as time_value " +
                "FROM reservation r " +
                "INNER JOIN reservation_time t ON r.time_id = t.id " +
                "WHERE r.id = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, RESERVATION_ROW_MAPPER, id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT " +
                "r.id AS reservation_id," +
                "r.name," +
                "r.date," +
                "t.id AS time_id," +
                "t.time AS time_value " +
                "FROM reservation r " +
                "INNER JOIN reservation_time t ON r.time_id = r.id";
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
    }

    @Override
    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        if (findById(id).isEmpty()) {
            throw new NotFoundReservationException();
        }
        
        jdbcTemplate.update(sql, id);
    }
}
