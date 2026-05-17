package roomescape;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sqlForDuplicateCheck = """
                SELECT EXISTS (
                    SELECT 1 
                    FROM reservation 
                    WHERE date = ? AND time = ?
                )
                """;

        return jdbcTemplate.queryForObject(
                sqlForDuplicateCheck,
                Boolean.class,
                date.toString(),
                time.toString()
        );
    }

    public Long insert(Reservation reservation) {
        String insertReservationSql = """
                INSERT INTO reservation (name, date, time) 
                VALUES (?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertReservationSql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Reservation> findAll() {
        String selectAllSql = """
                SELECT id, name, date, time 
                FROM reservation
                ORDER BY id ASC
                """;

        return jdbcTemplate.query(selectAllSql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("time"))
        ));
    }

    public int deleteById(Long id) {
        String deleteSql = """
                DELETE FROM reservation 
                WHERE id = ?
                """;

        return jdbcTemplate.update(deleteSql, id);
    }
}
