package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index =new AtomicLong(1);

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


        Reservation newReservation = Reservation.toEntity(reservation,index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    // Delete
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancel_reservation(@PathVariable Long id){


        Reservation newReservation = reservations.stream()
                .filter(it-> Objects.equals(it.getId(),id))
                .findFirst()
                //handle exception -> reservation is not found
                .orElseThrow(NotFoundReservationException::new);
        reservations.remove(newReservation);
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
