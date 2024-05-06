package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.exception.CustomException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation(){

        return "new-reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto.ReservationResponse>> getReservations(){

        return ResponseEntity.ok(reservationService.getReservations());
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto.ReservationResponse> postReservation(@RequestBody @Valid ReservationDto.ReservationRequest reservationRequest) {
         return reservationService.postReservation(reservationRequest);
    }

    @ResponseBody
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable(value = "id", required = true) Long id){

        reservationService.deleteReservation(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
