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
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import roomescape.controller.dto.ReservationCreate;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> getReservations() {
        return reservationService.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> post(@RequestBody @Valid ReservationCreate request) {
        Reservation reservation = reservationService.add(request.toReservation());
        
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
            .body(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.remove(id);

        return ResponseEntity.noContent().build();
    }
}
