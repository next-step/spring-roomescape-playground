package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

import java.util.List;

@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                    .map(ReservationResponse::from)
                    .toList();
    }
}
