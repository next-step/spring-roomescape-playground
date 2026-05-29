package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.exception.NotFoundReservationException;
import roomescape.model.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {

        String sql = """
                SELECT id, name, date, time
                FROM reservation
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->
                        new Reservation(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("date").toLocalDate(),
                                resultSet.getTime("time").toLocalTime()
                        )
        );
    }

    public Reservation save(Reservation reservation) {

        String sql = """
                INSERT INTO reservation(name, date, time)
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, reservation.name());
            statement.setDate(2, Date.valueOf((reservation.date())));
            statement.setTime(3, Time.valueOf(reservation.time()));

            return statement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    public void delete(Long id) {

        String sql = """
                DELETE FROM reservation
                WHERE id = ?
                """;

        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new NotFoundReservationException();
        }
    }
}
