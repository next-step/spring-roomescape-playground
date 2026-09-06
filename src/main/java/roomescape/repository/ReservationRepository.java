package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation ORDER BY id",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class)
                )
        );
    }

    public Reservation save(Reservation reservation) {
        Map<String, Object> parameters = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time", reservation.getTime()
        );

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public boolean deleteById(Long id) {
        int deleteCount = jdbcTemplate.update(
                "DELETE FROM reservation WHERE id = ?",
                id
        );

        return deleteCount > 0;
    }

    public boolean existsByNameAndDateAndTime(String name, LocalDate date, LocalTime time) {
        Boolean exists = jdbcTemplate.queryForObject(
                """
                SELECT EXISTS (
                    SELECT 1
                    FROM reservation
                    WHERE name = ? AND date = ? AND time = ?
                )
                """,
                Boolean.class,
                name,
                date,
                time
        );

        return exists;
    }
}
