package roomescape.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import roomescape.domain.Reservation;

import static java.lang.String.valueOf;


@Controller
public class ReservationController {
    private List<Reservation> reservations = new ArrayList<>(Arrays.asList(
            new Reservation("브라운", "2023-01-01", "10:00"),
            new Reservation("블루", "2023-02-01", "11:00"),
            new Reservation("옐로우", "2023-05-01", "12:00")
    ));

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations(){
        return ResponseEntity.ok(this.reservations);
    }

    @GetMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> getReservation(@PathVariable long id){
        for (Reservation reservation : this.reservations) {
            if (reservation.getId() == id) {
                return ResponseEntity.ok(reservation);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation newReservation, HttpServletRequest request) {
        this.reservations.add(newReservation);
        String uri = "/reservations/" + valueOf(newReservation.getId());
        return ResponseEntity.created(URI.create(uri)).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservatioin(@PathVariable long id){
        for (Reservation reservation : this.reservations) {
            if (reservation.getId() == id) {
                this.reservations.remove(reservation);
                break;
            }
        }
        return ResponseEntity.noContent().build();
    }

}
