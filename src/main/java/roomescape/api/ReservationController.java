package roomescape.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.SimpleReservationRepository;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final SimpleReservationRepository reservationRepository;

    public ReservationController(final SimpleReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
