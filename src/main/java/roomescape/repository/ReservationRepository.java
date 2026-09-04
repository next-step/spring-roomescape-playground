package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        return Reservation.createFromPersistedData(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                resultSet.getObject("time", LocalTime.class)
        );
    };

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(
                sql,
                reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setObject(2, reservation.getDate());
            preparedStatement.setObject(3, reservation.getTime());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return Reservation.createFromPersistedData(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
