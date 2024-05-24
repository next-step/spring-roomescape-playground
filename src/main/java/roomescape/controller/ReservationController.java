package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.dto.SaveReservationRequest;
import roomescape.service.ReservationService;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {
    private ReservationService reservationService;
    private TimeService timeService;

    public ReservationController(ReservationService reservationService, TimeService timeService) {
        this.reservationService = reservationService;
        this.timeService = timeService;
    }

    @GetMapping("/reservation")
    public String gotoReservation(){
        return "reservation";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations(){
        final var reservations = reservationService.findReservationById();
        return ResponseEntity.ok(reservations);
    }

    // TODO request에 time정보가 들어왔을 때 이를 Time으로 매핑하고, 검증해야 함
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> makeReservation(@RequestBody SaveReservationRequest request) {
        ReservationResponse response = reservationService.saveReservation(request);
        return ResponseEntity.created(URI.create("/reservations/" + response.id())).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable int id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
