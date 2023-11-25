package roomescape.reservation;

import error.Exception400;
//import error.HandleException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
@Controller
@RequestMapping("/reservations")
public class ReservationsController {
    private List<Reservation> reservations = new ArrayList<>();
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
        Reservation newReservation = Reservation.toEntity( reservation);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteReservations (@PathVariable Long id){
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(()-> new Exception400("해당 아이디를 찾을 수 없습니다"));
        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
