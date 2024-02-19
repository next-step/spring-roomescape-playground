package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.exception.Reservation.ReservationErrorMessage;
import roomescape.exception.Reservation.ReservationException;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TimeRepositoryImpl timeRepository;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate, TimeRepositoryImpl timeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeRepository = timeRepository;
    }

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        Time time = new Time(
                rs.getLong("time_id"),
                rs.getString("time_value")
        );

        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                time
        );
    };

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT "
                + "r.id AS reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id AS time_id, "
                + "t.time AS time_value "
                + "FROM reservation AS r INNER JOIN time AS t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation create(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                timeRepository.findById(reservationRequest.time())
        );

        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        return reservation.toEntity(keyHolder.getKey().longValue());
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int deleteRows = jdbcTemplate.update(sql, id);
        if (deleteRows == 0) {
            throw new ReservationException(ReservationErrorMessage.NOT_FOUND);
        }
        return deleteRows;
    }
}