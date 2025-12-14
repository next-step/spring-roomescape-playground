package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class ReservationController {

    private JdbcTemplate jdbcTemplate;
    public ReservationController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // render
    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    // RowMapper
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) ->
        new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );


    // Read
    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> findAllReservations() {
        String sql = "select id,name,date,time from reservation";
        return jdbcTemplate.query(sql,rowMapper);
    }

    // Create
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> add_reservation(@RequestBody Reservation reservation){
        // handle exception -> any required field empty
        if(isBlank(reservation.getName()) || isBlank(reservation.getDate()) || isBlank(reservation.getTime())){
            throw new BadRequestReservationException();
        }

        String sql = "insert into reservation (name,date,time) values (?,?,?)";
        // Create keyHolder
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1,reservation.getName());
                    ps.setString(2,reservation.getDate());
                    ps.setString(3,reservation.getTime());
                    return ps;
                },
                keyHolder
        );
        // Generated id
        Number key = keyHolder.getKey();
        if(key == null){
            throw new IllegalStateException("Failed to retrieve generated id");
        }
        Long id = key.longValue();
        return ResponseEntity.created(
                URI.create("/reservations/" + id)
        ).build();
    }

    // Delete
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void>  cancel_reservation(@PathVariable Long id){
        String sql = "delete from reservation where id = ?";
        int deleted = jdbcTemplate.update(sql,id);

        // handle exception
        if (deleted == 0) {
            throw new NotFoundReservationException();
        }
        return ResponseEntity.noContent().build();
    }

    // Exception Handler
    public class NotFoundReservationException extends RuntimeException {}
    public class BadRequestReservationException extends RuntimeException {}
    @ExceptionHandler({BadRequestReservationException.class, NotFoundReservationException.class})
    public ResponseEntity<Void> handleBadRequest(RuntimeException e){
        return ResponseEntity.badRequest().build();
    }

}
