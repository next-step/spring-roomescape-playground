package roomescape.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                select r.id as reservation_id, r.name, r.reservation_date,
                    t.id as time_id, t.start_at
                from reservations r
                inner join times t on r.time_id = t.id
                order by r.id
                """;
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "insert into reservations (name, reservation_date, time_id) values(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setString(1, reservation.getName());
                preparedStatement.setObject(2, reservation.getDate());
                preparedStatement.setObject(3, reservation.getTime().getId());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException exception) {
            throw new ReservationException(ReservationErrorCode.RESERVATION_CONFLICT);
        }

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("예약 저장 후 생성된 ID를 확인할 수 없습니다.");
        }

        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = """
                select r.id as reservation_id, r.name, r.reservation_date,
                    t.id as time_id, t.start_at
                from reservations r
                inner join times t on r.time_id = t.id
                where r.id = ?
                """;
        List<Reservation> reservations = jdbcTemplate.query(sql, this::mapRow, id);
        return reservations.stream().findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "delete from reservations where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean existsByDateAndTimeId(LocalDate date, Long timeId) {
        String sql = "select exists(select 1 from reservations where reservation_date = ? and time_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, timeId);
    }

    private Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getObject("reservation_date", LocalDate.class),
                new Time(
                        resultSet.getLong("time_id"),
                        resultSet.getObject("start_at", LocalTime.class)
                )
        );
    }
}
