package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationAddRequest;
import roomescape.dto.ReservationDTO;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getReservation(){

        List<ReservationDTO> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok().body(reservations);

    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationAddRequest reservationAddRequest){

        ReservationDTO newReservation = reservationService.bookReservation(reservationAddRequest);

        return ResponseEntity
                .status(201)
                .location(java.net.URI.create("/reservations/"+newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> deleteReservation(@PathVariable long id) {

        int row = reservationService.delete(id);

        if(row>0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
