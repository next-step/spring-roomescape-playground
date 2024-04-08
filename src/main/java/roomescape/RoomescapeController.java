package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RoomescapeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Reservation> reservations = new ArrayList<>();

    AtomicInteger atomic = new AtomicInteger(0);

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservationList = jdbcTemplate.query(
                "select id, name, time, date from reservation",
                (resultSet, rowNum) -> {

                    Reservation reservation = new Reservation(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("time"),
                            resultSet.getString("date")
                            );
                    return reservation;
                });

        return ResponseEntity.ok(reservationList);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        if(reservation.getName().isEmpty() || reservation.getDate().isEmpty() || reservation.getDate().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        Reservation newReservation = new Reservation(atomic.incrementAndGet(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable() int id) {
        if(reservations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reservation deletedReservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("삭제할 예약이 없습니다."));
        reservations.remove(deletedReservation);

        return null;
    }
}