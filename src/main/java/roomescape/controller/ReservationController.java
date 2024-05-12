package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = Arrays.asList(
           new Reservation(1L,"DongHo",
                   LocalDate.of(2024,05,21), LocalTime.of(7,23)),
            new Reservation(2L,"DongHo",
                    LocalDate.of(2024,05,21), LocalTime.of(7,23)),
            new Reservation(3L,"DongHo",
                    LocalDate.of(2024,05,21), LocalTime.of(7,23))
    );

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping(value = "/reservations")
    public ResponseEntity<List<Reservation>> reservations() {

        HttpHeaders headers = new HttpHeaders();

        if (reservations.isEmpty())
            return new ResponseEntity<>(Arrays.asList(), headers, HttpStatus.OK);

        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(reservations, headers, HttpStatus.OK);
    }

}
