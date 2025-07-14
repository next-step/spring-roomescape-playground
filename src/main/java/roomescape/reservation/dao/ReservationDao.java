package roomescape.reservation.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.reservation.model.Reservation;
import roomescape.time.model.Time;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
             select 
                 r.id as reservation_id, 
                 r.name, 
                 r.date, 
                 t.id as time_id, 
                 t.time as time_value 
            from reservation as r 
            inner join time as t on r.time_id = t.id
            """;

        return jdbcTemplate.query(
            sql,
            (resultSet, rowNum) -> {
                Time time = new Time(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime()
                );

                return new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    time
                );
            }

        );
    }

    public Reservation insert(Time time,
        String name, LocalDate date) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("date", date);
        parameters.put("time_id", time.getId());

        Long id = (Long) insert.executeAndReturnKey(parameters);

        return new Reservation(
            id,
            name,
            date,
            time
        );
    }

    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id) > 0;
    }
}
