package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationDto;

import roomescape.service.ReservationService;

import java.util.List;

@Controller
@Validated
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    // JDBC
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservation() {
        return reservationService.findAllReservation();
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> create(@Valid @RequestBody ReservationDto request){

        return reservationService.createReservation(request);

    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {

        return reservationService.deleteReservation(id);

    }
}
