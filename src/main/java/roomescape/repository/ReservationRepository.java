package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.Time;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {

        String sql = """
                SELECT 
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.time AS time_value
                FROM reservation r
                INNER JOIN time t
                ON r.time_id = t.id
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->
                        new Reservation(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("date").toLocalDate(),
                                new Time(
                                        resultSet.getLong("time_id"),
                                        resultSet.getTime("time_value").toLocalTime()
                                )
                        )
        );
    }

    public Reservation save(Reservation reservation) {

        String sql = """
                INSERT INTO reservation(name, date, time_id)
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, reservation.name());
            statement.setDate(2, Date.valueOf((reservation.date())));
            statement.setLong(3, reservation.time().id());

            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new IllegalStateException("생성된 ID를 가져올 수 없습니다.");
        }

        Long id = key.longValue();

        return new Reservation(
                id,
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    public int delete(Long id) {

        String sql = """
                DELETE FROM reservation
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
