package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dao.query.SelectQuery;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

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
        String sql = SelectQuery
                .select("r.id").as("reservation_id")
                .and("r.name")
                .and("r.date")
                .and("t.id").as("time_id")
                .and("t.time").as("time_value")
                .from("reservation r")
                .innerJoin("time t", "r.time_id = t.id")
                .build();

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
        String sql = SelectQuery
                .select("r.id").as("reservation_id")
                .and("r.name")
                .and("r.date")
                .and("t.id").as("time_id")
                .and("t.time").as("time_value")
                .from("reservation r")
                .innerJoin("time t", "r.time_id = t.id")
                .where("r.id = ?")
                .build();

        return jdbcTemplate.queryForObject(
                sql,
                rowMapper,
                id
        );
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
