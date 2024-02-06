package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import roomescape.dto.ReservationAddRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservationList() {
        List<ReservationResponse> reservationList = reservationService.findReservationList();

        return ResponseEntity.ok().body(reservationList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reservationSave(
        @RequestBody ReservationAddRequest reservationAddRequest) {
        ReservationResponse newReservation = reservationService.addReservation(reservationAddRequest);

        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> reservationRemove(@PathVariable Long id) {
        reservationService.removeReservation(id);

        return ResponseEntity.noContent().build();
    }
}
