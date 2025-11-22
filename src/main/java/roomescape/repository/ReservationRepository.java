package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> {
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    Date sqlDate = rs.getDate("date");
                    Time sqlTime = rs.getTime("time");
                    LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
                    LocalTime localTime = (sqlTime != null) ? sqlTime.toLocalTime() : null;
                    return new Reservation(id, name, localDate, localTime);
                }
        );
    }

    public Reservation save(ReservationRequest req) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, req.name());
            ps.setDate(2, Date.valueOf(req.date()));
            ps.setTime(3, Time.valueOf(req.time()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        Long id = (key != null) ? key.longValue() : null;
        return new Reservation(id, req.name(), req.date(), req.time());
    }

    public boolean deleteById(Long id) {
        int updated = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return updated > 0;
    }
}
