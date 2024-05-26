package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.db.ReservationEntity;
import roomescape.model.ReservationRequest;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationApiController {

   private final ReservationService reservationService;

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationEntity>> reservations() {

        List<ReservationEntity> lists = reservationService.findAllList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(lists);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationEntity> createReservation(
            @Valid
            @RequestBody ReservationRequest reservationRequest
    ) {

        Long nowId = Long.valueOf(reservationService.countReservation()) + 1;

        reservationService.insertReservation(reservationRequest);

        ReservationEntity reservationEntity = reservationService.findReservationById(nowId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/reservations/" + Long.toString(nowId)));

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(reservationEntity);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) {

        reservationService.deleteReservation(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
