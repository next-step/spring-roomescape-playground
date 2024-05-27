package roomescape.reservation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.exception.BadRequestReservationException;
import roomescape.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations(){
        List<ReservationResponse> responseDtoList = reservationService.findAllReservations();

        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest reservationRequest){
        reservationRequest.validate();
        ReservationResponse responseDto = reservationService.addReservation(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + responseDto.getId()))
                .body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id){
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestReservationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
