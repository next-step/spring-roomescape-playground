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
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationSaveRequestDto;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String goReservationPage() {
        return "new-reservation";
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationSaveRequestDto requestDto) {
        ReservationResponseDto reservationResponseDto = reservationService.createReservation(requestDto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationResponseDto.getId()))
                .body(reservationResponseDto);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> readAllReservations() {

        return ResponseEntity.ok().body(reservationService.getAllReservations());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
