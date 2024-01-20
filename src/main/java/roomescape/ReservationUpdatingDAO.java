package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time", reservation.getTime());

        return simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    public int delete(long id){
        String sql = "DELETE FROM reservation WHERE id = ?";
        int num = jdbcTemplate.update(sql, id);
        if(num == 0){
            throw new GlobalExceptionHandler.NotFoundReservationException("Reservation is not found with id: " + id);
        }
        return num;
    }

    @ExceptionHandler(GlobalExceptionHandler.NotFoundReservationException.class)
    public ResponseEntity handleNotFoundReservationException(GlobalExceptionHandler.NotFoundReservationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
