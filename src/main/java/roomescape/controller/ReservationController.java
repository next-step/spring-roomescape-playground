package roomescape.controller;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

        @GetMapping
        public ResponseEntity<List<ReservationRequestDto>> readReservations() {
            List<ReservationRequestDto> reservationDtos = reservationService.findAllReservations();
            return ResponseEntity.ok().body(reservationDtos);
        }

        @PostMapping
        public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequestDto reservationDTO) {
            Reservation reservation = reservationService.createReservation(reservationDTO);
            return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        }


}
