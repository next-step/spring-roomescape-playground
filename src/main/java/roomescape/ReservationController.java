package roomescape;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read(){
        String sql = "select id, name, date, time from reservation";
        List<Reservation> reservations = jdbcTemplate.query(
            sql, (resultSet, rowNum) -> {
                Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
                );
                return reservation;
            });
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
        if( reservation.getName() == null ||
            reservation.getDate() == null ||
            reservation.getTime() == null )
            throw new NotFoundReservationException();

        String insertSql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, reservation.getName(), reservation.getDate(), reservation.getTime());

        String findSql = "SELECT id, name, date, time FROM reservation WHERE name = ? AND date = ? AND time = ?";
        Reservation newReservation = jdbcTemplate.queryForObject(
            findSql, (resultSet, rowNum) -> {
                Reservation tmpReservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
                );
                return tmpReservation;
            }, reservation.getName(), reservation.getDate(), reservation.getTime());

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleException(NotFoundReservationException e){
        return ResponseEntity.badRequest().build();
    }
}
