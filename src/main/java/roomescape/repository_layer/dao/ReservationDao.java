package roomescape.repository_layer.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public Long insert(Reservation reservation) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getTime().getId()
        );
        Number id = insertActor.executeAndReturnKey(parameters);
        return id.longValue();
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.time
                FROM reservation AS r
                INNER JOIN time AS t ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Reservation.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        Time.of(
                                rs.getLong("time_id"),
                                rs.getString("time")
                        )
                ));
    }


    public int delete(Long id) {
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
