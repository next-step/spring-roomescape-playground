package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.CreateReservationRequestDto;
import roomescape.dto.CreateReservationResponseDto;
import roomescape.dto.ReadReservationDto;
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
        public ResponseEntity<List<ReadReservationDto>> readReservations() {
            List<ReadReservationDto> reservationDtos = reservationService.findAllReservations();
            return ResponseEntity.ok().body(reservationDtos);
        }

        @PostMapping
        public ResponseEntity<CreateReservationResponseDto> createReservation(@RequestBody CreateReservationRequestDto reservationRequestDto) {
            CreateReservationResponseDto reservationResponseDto = reservationService.createReservation(reservationRequestDto);
            return ResponseEntity.created(URI.create("/reservations/" + reservationResponseDto.getReservationId())).body(reservationResponseDto);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        }

}
