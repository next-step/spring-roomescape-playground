package roomescape.reservation.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.reservation.dto.ReservationRequestDTO;
import roomescape.reservation.dto.ReservationResponseDTO;
import roomescape.reservation.service.ReservationService;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public String displayReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseDTO>> getReservations() {
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ReservationResponseDTO> addReservation(@RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = reservationService.addReservation(request);
        URI location = URI.create("/reservations/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
