package roomescape.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
    );

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                rowMapper
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setString(3, reservation.getTime().toString());
            return preparedStatement;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return reservation.withId(id);
    }

    @Override
    public boolean deleteById(long id) {
        int deleteCount = jdbcTemplate.update(
                "DELETE FROM reservation WHERE id = ?",
                id
        );
        return deleteCount > 0;
    }
}
