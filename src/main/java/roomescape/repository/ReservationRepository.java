package roomescape.repository;

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
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                LocalTime.parse(resultSet.getString("time"))
        );
    };

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                rowMapper
        );
    }

    public Reservation save(String name, LocalDate date, LocalTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date.toString());
            preparedStatement.setString(3, time.toString());
            return preparedStatement;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return new Reservation(id, name, date, time);
    }

    public int deleteById(long id) {
        int deleteCount = jdbcTemplate.update(
                "DELETE FROM reservation WHERE id = ?",
                id
        );
        return deleteCount;
    }
}
