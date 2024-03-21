package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.CreateReservationRequestDto;
import roomescape.dto.CreateReservationResponseDto;
import roomescape.dto.ReadReservationDto;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
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
        public ResponseEntity<CreateReservationResponseDto> createReservation(@Valid @RequestBody CreateReservationRequestDto reservationRequestDto) {
            CreateReservationResponseDto reservationResponseDto = reservationService.createReservation(reservationRequestDto);

            URI location = URI.create("/reservations/" + reservationResponseDto.getReservationId());

            return ResponseEntity.created(location).body(reservationResponseDto);
        }



    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        }

}
