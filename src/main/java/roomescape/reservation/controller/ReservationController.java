package roomescape.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.mapper.ReservationRowMapper;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.exception.InvalidReservationException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Reservation> reservations = new ArrayList<>();

    private AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation"; //reservation.html 파일을 반환합니다.
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){

        if(reservation.getName().isEmpty() || reservation.getTime().isEmpty() || reservation.getDate().isEmpty()){
            throw new InvalidReservationException("Invalid reservation data, Field Empty");
        }

        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        Reservation reservation = reservations.stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst()
                .orElseThrow(() -> new InvalidReservationException("Reservation not found with id: " + id));

        reservations.remove(reservation);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
