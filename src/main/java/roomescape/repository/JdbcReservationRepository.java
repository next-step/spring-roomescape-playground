package roomescape.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
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
        String sql = "select id, name, reservation_date, reservation_time from reservations "
                + "order by reservation_date, reservation_time";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "insert into reservations (name, reservation_date, reservation_time) values(?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setString(1, reservation.getName());
                preparedStatement.setObject(2, reservation.getDate());
                preparedStatement.setObject(3, reservation.getTime());
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
        String sql = "select id, name, reservation_date, reservation_time from reservations where id = ?";
        List<Reservation> reservations = jdbcTemplate.query(sql, this::mapRow, id);
        return reservations.stream().findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "delete from reservations where id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "select exists(select 1 from reservations where reservation_date = ? and reservation_time = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, time);
    }

    private Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("reservation_date", LocalDate.class),
                resultSet.getObject("reservation_time", LocalTime.class)
        );
    }
}
