package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.List;
import java.util.Optional;

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
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                )
        );
    }

    public Optional<Reservation> findById(Long id) {
        String sql = """
                SELECT id, name, date, time
                FROM reservation
                WHERE id = ?
                """;

        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ),
                id
        );

        return reservations.stream().findFirst();
    }

    public Reservation save(ReservationRequest request) {
        String sql = """
                INSERT INTO reservation (name, date, time)
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var preparedStatement =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, request.getName());
            preparedStatement.setString(2, request.getDate());
            preparedStatement.setString(3, request.getTime());

            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
    }

    public Reservation update(Long id, ReservationRequest request) {
        findById(id)
                .orElseThrow(NotFoundReservationException::new);

        String sql = """
                UPDATE reservation
                SET name = ?, date = ?, time = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                request.getName(),
                request.getDate(),
                request.getTime(),
                id
        );

        return new Reservation(
                id,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
    }

    public void delete(Long id) {
        findById(id)
                .orElseThrow(NotFoundReservationException::new);

        String sql = """
                DELETE FROM reservation
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
