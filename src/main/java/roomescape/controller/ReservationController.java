package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationAddRequest;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservation(){

        List<Reservation> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok().body(reservations);

    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationAddRequest reservationAddRequest){

        //빈 값 들어왔을 때의 예외 처리
        if(reservationAddRequest.getName() == null || reservationAddRequest.getName().isEmpty() || reservationAddRequest.getDate() == null || reservationAddRequest.getTime() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 'time' 필드가 'Time' 객체가 아닐 때의 예외 처리
        if(reservationAddRequest.getTime().isInvalid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Reservation newReservation = reservationService.bookReservation(reservationAddRequest);

        return ResponseEntity
                .status(201)
                .location(java.net.URI.create("/reservations/"+newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) throws Exception {

        int row = reservationService.delete(id);

        if(row>0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
