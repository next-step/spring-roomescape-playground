package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Map;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Reservation reservation) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time", reservation.getTime()
        );
        Number id = insertActor.executeAndReturnKey(parameters);
        return id.longValue();
    }

    public List<Reservation> findAll() {
        String sql = "select id,name,date,time from reservation";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = Reservation.newReservationFromDb(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")

                    );
                    return reservation;
                });
    }


    public int delete(Long id) {
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
