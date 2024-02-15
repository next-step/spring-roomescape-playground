package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.Reservation;
import roomescape.service.ReservationApiService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    @Autowired
    private ReservationApiService reservationApiService;

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations(){
        return ResponseEntity.ok(reservationApiService.loadReservationList());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservations(@RequestBody Reservation reservation){
        Reservation newReservation = reservationApiService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        reservationApiService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
