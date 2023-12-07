package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Repository
public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        Map<String, Object> source = new HashMap<>();
        source.put("id", reservation.getId());
        source.put("name", reservation.getName());
        source.put("date", reservation.getDate());
        source.put("time_id", reservation.getTime().getId());
        Number key = simpleJdbcInsert.executeAndReturnKey(source);
        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
            """
                     select 
                     r.id as rservation_id, 
                     r.name, 
                     r.date, 
                     t.id as time_id, 
                     t.time as time_value 
                     from reservation as r 
                     inner join time as t on r.time_id = t.id
                """
            , (rs, rowNum) -> {
                Time time = new Time(
                    rs.getLong("time_id"),
                    rs.getTime("time_value").toLocalTime()
                );

                return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    time
                );
            }
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Long result = jdbcTemplate.queryForObject("select count(*) from reservation where id = ?", Long.class, id);
        if (result == null) {
            return false;
        }
        return result > 0;
    }
}
