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

    public boolean existsByDateAndTimeId(LocalDate date, Long timeId) {
        String sqlForDuplicateCheck = """
                SELECT EXISTS (
                    SELECT 1 
                    FROM reservation 
                    WHERE date = ? AND time_id = ?
                )
                """;
        return jdbcTemplate.queryForObject(
                sqlForDuplicateCheck, Boolean.class, date.toString(), timeId);
    }

    public Long insert(Reservation reservation) {
        String insertSql = """
                INSERT INTO reservation (name, date, time_id) 
                VALUES (?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Reservation> findAll() {
        String selectAllSql = """
                SELECT 
                    r.id as reservation_id, 
                    r.name, 
                    r.date, 
                    t.id as time_id, 
                    t.time as time_value 
                FROM reservation as r 
                INNER JOIN time as t ON r.time_id = t.id
                ORDER BY r.id ASC
                """;

        return jdbcTemplate.query(selectAllSql, (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                new Time(rs.getLong("time_id"), LocalTime.parse(rs.getString("time_value")))
        ));
    }

    public int deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(deleteSql, id);
    }
}
