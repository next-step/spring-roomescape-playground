package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

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
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setObject(2, reservation.getDate());
            preparedStatement.setObject(3, reservation.getTime());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "select id, name, reservation_date, reservation_time from reservations where id = ?";
        List<Reservation> reservations = jdbcTemplate.query(sql, this::mapRow, id);
        return reservations.stream().findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
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
