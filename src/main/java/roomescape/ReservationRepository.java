package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;
import java.time.LocalDate;

@Repository
public class ReservationRepository {

    private static final String SELECT_SQL = """
            SELECT r.id, r.name, r.date, t.id AS time_id, t.time
            FROM reservation r
            JOIN reservation_time t ON r.time_id = t.id
            """;

    private static final RowMapper<Reservation> ROW_MAPPER =
            (rs, rowNum) -> new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            rs.getObject("time", LocalTime.class)
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                SELECT_SQL + " ORDER BY r.id",
                ROW_MAPPER
        );
    }

    public Optional<Reservation> findById(Long id) {
        return jdbcTemplate.query(
                        SELECT_SQL + " WHERE r.id = ?",
                        ROW_MAPPER,
                        id
                )
                .stream()
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        String sql = """
                INSERT INTO reservation (name, date, time_id)
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, reservation.getName());
            statement.setString(2, reservation.getDate().toString());
            statement.setLong(3, reservation.getTime().getId());

            return statement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public int update(Reservation reservation) {
        String sql = """
                UPDATE reservation
                SET name = ?, date = ?, time_id = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getTime().getId(),
                reservation.getId()
        );
    }

    public int delete(Long id) {
        return jdbcTemplate.update(
                "DELETE FROM reservation WHERE id = ?",
                id
        );
    }
}
