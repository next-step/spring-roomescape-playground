package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.dto.ReservationAddRequest;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservationList = reservationService.getAllReservations();

        return ResponseEntity.ok().body(reservationList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationAddRequest reservationAddRequest) {
        Reservation newReservation = reservationService.addReservation(reservationAddRequest);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.removeReservation(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getAllTimes() {
        List<Time> timeList = reservationService.getAllTimes();

        return ResponseEntity.ok().body(timeList);
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        Time newTime = reservationService.addTime(time);

        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationService.removeTime(id);

        return ResponseEntity.noContent().build();
    }
}
