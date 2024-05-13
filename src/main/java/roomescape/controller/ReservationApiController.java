package roomescape.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReservationApiController {

    private final ReservationRepository reservationRepository;

    public ReservationApiController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations(){
        List<Reservation> reservations = reservationRepository.findAll();

        List<ReservationResponseDto> responseDtoList = reservations.stream()
                .map(ReservationResponseDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservations);
    }
}
