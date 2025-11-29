package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    private static final String BASE_SELECT_SQL = """
        SELECT 
            r.id as reservation_id, 
            r.name, 
            r.date, 
            t.id as time_id, 
            t.time as time_value 
        FROM reservation r 
        INNER JOIN time t ON r.time_id = t.id
        """;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    new Time(resultSet.getLong("time_id"), resultSet.getString("time_value"))
            );

    public List<Reservation> findAll() {
        String sql = BASE_SELECT_SQL;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public long insert(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time_id", reservation.getTime().getId());

        Number key = simpleInsert.executeAndReturnKey(params);
        return key.longValue();
    }

     public Reservation findById(long id) {
         return jdbcTemplate.queryForObject(
                 BASE_SELECT_SQL + " WHERE r.id = ?",
                 rowMapper,
                 id
         );
     }
 
    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
