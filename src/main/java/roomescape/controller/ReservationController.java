package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Reservation reservation1 = new Reservation(1, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0));
        Reservation reservation2 = new Reservation(2, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0));

        List<Reservation> reservations = List.of(reservation1, reservation2);

        return new ResponseEntity<>(reservations, headers, HttpStatus.OK);
    }
}
