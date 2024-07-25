package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class RoomescapeController {

    private final ReservationService reservationService;

    public RoomescapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "new-reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> createReservation(@RequestBody ReservationDto reservationDto) {
        try {
            Reservation reservation = reservationService.addReservation(reservationDto);
            URI location = UriComponentsBuilder.fromPath("/reservations/{id}")
                    .buildAndExpand(reservation.getId())
                    .toUri();
            return ResponseEntity.created(location).body(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> deleteReservation(@PathVariable int id){
        try {
            reservationService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
