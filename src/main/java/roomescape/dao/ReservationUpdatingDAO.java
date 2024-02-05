package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import roomescape.domain.Reservation;
import roomescape.exception.GlobalExceptionHandler;
import roomescape.exception.NotFoundException;

@Repository
public class ReservationUpdatingDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationUpdatingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Number save(Reservation reservation){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getTime());

        return simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    public int delete(long id){
        String sql = "DELETE FROM reservation WHERE id = ?";

        int num = jdbcTemplate.update(sql, id);
        if(num == 0){
            throw new NotFoundException("Reservation is not found with id: " + id);
        }
        return num;
    }
}