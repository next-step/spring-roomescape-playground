package roomescape.reservation;

import error.Exception400;
//import error.HandleException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
@Controller
@RequestMapping("/reservations")
public class ReservationsController {
    private JdbcTemplate jdbcTemplate;

    public ReservationsController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> GetReservations (){

        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservations1 = jdbcTemplate.query(sql , (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time"));
            return reservation;
        });
        return ResponseEntity.ok().body(reservations1);
    }


    @PostMapping
    public ResponseEntity<Reservation> PostReservations ( @RequestBody Reservation reservation ){
        if (reservation.getName().isEmpty() ||reservation.getDate().isEmpty() || reservation.getTime().isEmpty()) {
            throw new Exception400("Date and time cannot be null");
        }
        Reservation newReservation = Reservation.toEntity(reservation);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, newReservation.getName());
            ps.setString(2, newReservation.getDate());
            ps.setString(3, newReservation.getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        newReservation.setId(id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(newReservation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteReservations (@PathVariable Long id){
        String sql = "DELETE FROM reservation WHERE id = ?";
        int rowNum = jdbcTemplate.update(sql, Long.valueOf(id));

        return ResponseEntity.noContent().build();
    }
}
